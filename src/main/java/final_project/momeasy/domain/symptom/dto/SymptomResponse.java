package final_project.momeasy.domain.symptom.dto;

public record SymptomResponse(
        String code,    // 예: FEVER
        String name     // 예: 발열(38도 이상)
) {}
