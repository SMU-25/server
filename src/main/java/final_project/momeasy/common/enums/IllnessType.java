package final_project.momeasy.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IllnessType {
    아토피("아토피"),
    천식("천식"),
    폐_질환("폐 질환"),
    고혈압("고혈압"),
    심장_질환("심장 질환"),
    간_질환("간 질환"),
    신장_질환("신장 질환"),
    면역력_저하("면역력 저하"),
    해당_없음("해당 없음");

    private final String illness;
}
