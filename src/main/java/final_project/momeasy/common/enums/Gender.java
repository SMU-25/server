package final_project.momeasy.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    GIRL("여자"),
    BOY("남자");

    private final String gender;
}
