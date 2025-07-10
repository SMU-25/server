package final_project.momeasy.domain.symptom.config;

import final_project.momeasy.common.enums.SymptomType;
import final_project.momeasy.domain.symptom.entity.Symptom;
import final_project.momeasy.domain.symptom.repository.SymptomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SymptomInitializer implements CommandLineRunner {

    private final SymptomRepository symptomRepository;

    @Override
    public void run(String... args) {
        if (symptomRepository.count() == 0) {
            for (SymptomType type : SymptomType.values()) {
                Symptom symptom = Symptom.builder()
                        .symptom(type)
                        .build();
                symptomRepository.save(symptom);
            }
        }
    }
}
