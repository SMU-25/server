package final_project.momeasy.domain.notification.dto;

import final_project.momeasy.common.enums.NotificationType;
import final_project.momeasy.domain.notification.entity.Notification;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationResponse {

    private Long notificationId;
    private NotificationType type;     // CARE 고정
    private String message;
    private Float fever;               // 체온 (nullable)
    private Float temperature;         // 온도 (nullable)
    private Float humidity;            // 습도 (nullable)
    private boolean isRead;
    private LocalDateTime createdAt;
    private String childName;

    public static NotificationResponse from(Notification notification) {
        return NotificationResponse.builder()
                .notificationId(notification.getId())
                .type(notification.getType())
                .message(notification.getMessage())
                .fever(notification.getFever())
                .temperature(notification.getTemperature())
                .humidity(notification.getHumidity())
                .isRead(notification.isRead())
                .createdAt(notification.getCreatedAt())
                .childName(notification.getChild().getName())
                .build();
    }
}
