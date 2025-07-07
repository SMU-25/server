package final_project.momeasy.domain.illness.config;

import final_project.momeasy.common.enums.IllnessType;
import final_project.momeasy.domain.illness.entity.Illness;
import final_project.momeasy.domain.illness.repository.IllnessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class IllnessInitializer implements CommandLineRunner {

    private final IllnessRepository illnessRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (illnessRepository.count() == 0) {
            for (IllnessType type : IllnessType.values()) {
                Illness illness = Illness.builder()
                        .illnessType(type)
                        .build();
                illnessRepository.save(illness);
            }
        }
    }
}
