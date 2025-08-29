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

    public static Notification from(
            Parent parent,
            Child child,
            String message,
            Float fever,
            Float temperature,
            Float humidity
    ) {
        Notification notification = Notification.builder()
                .type(NotificationType.CARE)
                .message(message)
                .fever(fever)
                .temperature(temperature)
                .humidity(humidity)
                .isRead(false)
                .build();

        notification.setParent(parent);
        notification.setChild(child);
        return notification;
    }
}
