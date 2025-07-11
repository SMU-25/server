package final_project.momeasy.domain.notification.converter;

import final_project.momeasy.common.enums.NotificationType;
import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.notification.dto.NotificationRequest;
import final_project.momeasy.domain.notification.dto.NotificationResponse;
import final_project.momeasy.domain.notification.entity.Notification;
import final_project.momeasy.domain.parent.entity.Parent;

public class NotificationConverter {

    public static NotificationResponse toResponse(Notification notification) {
        return NotificationResponse.from(notification);
    }

    public static Notification fromRequest(NotificationRequest request, Parent parent, Child child) {
        return Notification.builder()
                .parent(parent)
                .child(child)
                .type(NotificationType.CARE)
                .message(request.getMessage())
                .fever(request.getFever())
                .temperature(request.getTemperature())
                .humidity(request.getHumidity())
                .isRead(false)
                .build();
    }
}
