package final_project.momeasy.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SymptomType {
    발열("발열"),
    구토("구토"),
    경련("경련"),
    코피("코피"),
    설사("설사"),
    피부_발진("피부 발진"),
    실신("실신"),
    호흡_곤란("호흡 곤란"),
    기침("기침"),
    콧물("콧물"),
    황달("황달"),
    해당_없음("해당 없음");

    private final String symptom;
}
