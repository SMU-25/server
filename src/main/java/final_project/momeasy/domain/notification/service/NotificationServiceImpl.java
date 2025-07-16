package final_project.momeasy.domain.notification.service;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.notification.converter.NotificationConverter;
import final_project.momeasy.domain.notification.dto.NotificationResponse;
import final_project.momeasy.domain.notification.entity.Notification;
import final_project.momeasy.domain.notification.exception.NotificationErrorCode;
import final_project.momeasy.domain.notification.exception.NotificationException;
import final_project.momeasy.domain.notification.repository.NotificationRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.apiPayload.CursorResponse;
import final_project.momeasy.global.fcm.entity.FcmToken;
import final_project.momeasy.global.fcm.service.FcmService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final FcmService fcmService;

    @Override
    @Transactional
    public CursorResponse<NotificationResponse> getNotifications(Parent parent, Long cursor, int size) {
        Pageable pageable = PageRequest.of(0, size);
        List<Notification> notifications;

        if (cursor == null) {
            notifications = notificationRepository.findByParentIdOrderByIdDesc(parent.getId(), pageable);
        } else {
            notifications = notificationRepository.findByParentIdAndIdLessThanOrderByIdDesc(parent.getId(), cursor, pageable);
        }

        List<NotificationResponse> content = notifications.stream()
                .map(NotificationConverter::toResponse)
                .toList();

        Long nextCursor = content.isEmpty() ? null : content.get(content.size() - 1).getNotificationId();
        boolean hasNext = notifications.size() == size;

        return new CursorResponse<>(content, hasNext ? nextCursor : null, hasNext);
    }

    @Override
    @Transactional
    public void markAsRead(Parent parent, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationException(NotificationErrorCode.NOT_FOUND));

        if (!notification.getParent().getId().equals(parent.getId())) {
            throw new NotificationException(NotificationErrorCode.UNAUTHORIZED_ACCESS);
        }

        notification.markAsRead();
    }

    // 직접 파라미터 전달 방식
    @Transactional
    public void createNotification(Parent parent, Child child, String message, Float fever, Float temperature, Float humidity) {
        Notification notification = NotificationConverter.from(
                parent,
                child,
                message,
                fever,
                temperature,
                humidity
        );

        notificationRepository.save(notification);

        List<FcmToken> tokens = parent.getFcmTokens();
        if (!tokens.isEmpty()) {
            String token = tokens.get(0).getToken();
            fcmService.sendNotification(token, "케어 알림", message);
        } else {
            System.out.println("⚠️ FCM 토큰 없음 - 푸시 생략");
        }
    }
}
