package final_project.momeasy.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecordState {
    HUMAN("사람 체온"),
    NOT_HUMAN("사람 체온 아님");

    private final String state;
}
