package final_project.momeasy.domain.symptom.service;

import final_project.momeasy.domain.symptom.dto.IllnessResponse;
import final_project.momeasy.domain.illness.entity.Illness;
import final_project.momeasy.domain.illness.repository.IllnessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IllnessService {

    private final IllnessRepository illnessRepository;

    public List<IllnessResponse> getAllIllnesses() {
        List<Illness> illnesses = illnessRepository.findAll();

        return illnesses.stream()
                .map(illness -> new IllnessResponse(
                        illness.getId(),
                        illness.getIllnessType().getDisplayName() // Enum에 displayName이 있다고 가정
                ))
                .toList();
    }

    public Illness getIllnessById(Long id) {
        return illnessRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Illness not found: " + id));
    }
}
