package final_project.momeasy.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DayRange {
    Day1("1일"),
    Day3("3일"),
    Day7("7일");

    private final String dayrange;

}
