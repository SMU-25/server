package final_project.momeasy.domain.notification.service;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.notification.converter.NotificationConverter;
import final_project.momeasy.domain.notification.dto.NotificationResponse;
import final_project.momeasy.domain.notification.entity.Notification;
import final_project.momeasy.domain.notification.exception.NotificationErrorCode;
import final_project.momeasy.domain.notification.exception.NotificationException;
import final_project.momeasy.domain.notification.repository.NotificationRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.setting.entity.Setting;
import final_project.momeasy.global.apiPayload.CursorResponse;
import final_project.momeasy.global.fcm.service.FcmService;
import final_project.momeasy.global.fcm.service.FcmTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final FcmService fcmService;
    private final FcmTokenService fcmTokenService;

    @Override
    @Transactional(readOnly = true)
    public CursorResponse<NotificationResponse> getNotifications(Parent parent, Long cursor, int size) {
        Pageable pageable = PageRequest.of(0, size);
        List<Notification> notifications = (cursor == null)
                ? notificationRepository.findByParentIdOrderByIdDesc(parent.getId(), pageable)
                : notificationRepository.findByParentIdAndIdLessThanOrderByIdDesc(parent.getId(), cursor, pageable);

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

    /** 케어 알림 */
    @Transactional
    public void createCareNotification(Parent parent, Child child, String message,
                                       Float fever, Float temperature, Float humidity) {
        Notification notification = NotificationConverter.from(parent, child, message, fever, temperature, humidity);
        notificationRepository.save(notification);

        Setting setting = parent.getSetting();
        boolean allowPush =
                Boolean.TRUE.equals(setting.getAll_alarm()) &&
                        Boolean.TRUE.equals(setting.getCare_alarm());
        if (!allowPush) {
            log.info("케어 알림 푸시 OFF - parentId={}", parent.getId());
            return;
        }

        sendPushToParent(parent, "케어 알림", message);
    }

    /** 이벤트 알림 */
    @Transactional
    public void createEventNotification(Parent parent, String message) {
        Notification notification = NotificationConverter.from(parent, null, message, null, null, null);
        notificationRepository.save(notification);

        Setting setting = parent.getSetting();
        boolean allowPush =
                Boolean.TRUE.equals(setting.getAll_alarm()) &&
                        Boolean.TRUE.equals(setting.getMarketing_alarm());
        if (!allowPush) {
            log.info("이벤트 알림 푸시 OFF - parentId={}", parent.getId());
            return;
        }

        sendPushToParent(parent, "이벤트 알림", message);
    }

    private void sendPushToParent(Parent parent, String title, String body) {
        List<String> tokens = fcmTokenService.getTokenStringsByParent(parent);
        if (tokens == null || tokens.isEmpty()) {
            log.warn("FCM 토큰 없음 - parentId={}", parent.getId());
            return;
        }
        for (String token : tokens) {
            try {
                fcmService.sendNotification(token, title, body);
            } catch (Exception e) {
                String tail = token.length() > 6 ? token.substring(token.length() - 6) : token;
                log.warn("FCM 전송 실패 - parentId={}, token=***{}, err={}", parent.getId(), tail, e.getMessage());
            }
        }
    }
}
