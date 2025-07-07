package final_project.momeasy.domain.symptom.converter;

import final_project.momeasy.domain.symptom.dto.SymptomResponse;
import final_project.momeasy.domain.symptom.entity.Symptom;

public class SymptomConverter {

    public static SymptomResponse toDto(Symptom symptom) {
        return new SymptomResponse(
                symptom.getSymptomType().name(),             // "FEVER"
                symptom.getSymptomType().getSymptom()        // "발열(38도 이상)"
        );
    }
}
