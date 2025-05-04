package final_project.momeasy.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Seizure {
    YES("있음"),
    NONE("없음"),
    UNKNOWN("모름");

    private final String seizure;
}
