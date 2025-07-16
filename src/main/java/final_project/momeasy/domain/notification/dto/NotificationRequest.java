package final_project.momeasy.domain.notification.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class NotificationRequest {

    @NotNull(message = "알림 메시지는 필수입니다.")
    private String message;

    private Float fever;           // 체온 (nullable)
    private Float temperature;     // 온도 (nullable)
    private Float humidity;        // 습도 (nullable)

    @NotNull(message = "child_Id는 필수입니다.")
    private Long childId;
}
