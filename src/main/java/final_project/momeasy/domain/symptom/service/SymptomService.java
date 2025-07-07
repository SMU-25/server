package final_project.momeasy.domain.symptom.service;

import final_project.momeasy.domain.symptom.converter.SymptomConverter;
import final_project.momeasy.domain.symptom.dto.SymptomResponse;
import final_project.momeasy.domain.symptom.repository.SymptomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SymptomService {

    private final SymptomRepository symptomRepository;

    public List<SymptomResponse> getAllSymptoms() {
        return symptomRepository.findAll().stream()
                .map(SymptomConverter::toDto)
                .toList();
    }
}
