package final_project.momeasy.domain.notification.service;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.notification.converter.NotificationConverter;
import final_project.momeasy.domain.notification.dto.NotificationRequest;
import final_project.momeasy.domain.notification.dto.NotificationResponse;
import final_project.momeasy.domain.notification.entity.Notification;
import final_project.momeasy.domain.notification.exception.NotificationErrorCode;
import final_project.momeasy.domain.notification.exception.NotificationException;
import final_project.momeasy.domain.notification.repository.NotificationRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.parent.entity.ParentChild;
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
    public List<NotificationResponse> getNotifications(Parent parent) {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        return notificationRepository.findByParentId(parent.getId(), pageable)
                .stream()
                .map(NotificationConverter::toResponse)
                .toList();
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

    @Override
    @Transactional
    public void createNotification(Parent parent, NotificationRequest request) {
        Child child = parent.getParentChild().stream()
                .map(ParentChild::getChild)
                .filter(c -> c.getId().equals(request.getChildId()))
                .findFirst()
                .orElseThrow(() -> new NotificationException(NotificationErrorCode.UNAUTHORIZED_ACCESS));

        // ✅ 수정됨: Converter 사용
        Notification notification = NotificationConverter.fromRequest(request, parent, child);
        notificationRepository.save(notification);

        List<FcmToken> tokens = parent.getFcmTokens();
        if (!tokens.isEmpty()) {
            String token = tokens.get(0).getToken();
            fcmService.sendNotification(token, "케어 알림", request.getMessage());
        } else {
            System.out.println("⚠️ FCM 토큰 없음 - 푸시 생략");
        }
    }
}
