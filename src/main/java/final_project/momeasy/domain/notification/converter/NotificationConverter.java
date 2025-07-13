package final_project.momeasy.domain.notification.converter;

import final_project.momeasy.common.enums.NotificationType;
import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.notification.dto.NotificationResponse;
import final_project.momeasy.domain.notification.entity.Notification;
import final_project.momeasy.domain.parent.entity.Parent;

public class NotificationConverter {

    public static NotificationResponse toResponse(Notification notification) {
        return NotificationResponse.from(notification);
    }

    // 🔧 직접 파라미터 전달 방식
    public static Notification from(
            Parent parent,
            Child child,
            String message,
            Float fever,
            Float temperature,
            Float humidity
    ) {
        return Notification.builder()
                .parent(parent)
                .child(child)
                .type(NotificationType.CARE)
                .message(message)
                .fever(fever)
                .temperature(temperature)
                .humidity(humidity)
                .isRead(false)
                .build();
    }
}
