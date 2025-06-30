package final_project.momeasy.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DayRange {
    DAY1("1일"),
    DAY3("3일"),
    DAY7("7일");

    private final String dayrange;

}
