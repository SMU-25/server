package final_project.momeasy.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationType {
    CARE("케어 알림");  // 체온 + 온습도 알림 통합

    private final String description;
}
