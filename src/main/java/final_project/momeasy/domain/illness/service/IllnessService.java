package final_project.momeasy.domain.illness.service;

import final_project.momeasy.domain.illness.converter.IllnessConverter;
import final_project.momeasy.domain.illness.dto.IllnessResponse;
import final_project.momeasy.domain.illness.repository.IllnessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IllnessService {

    private final IllnessRepository illnessRepository;

    public List<IllnessResponse> getAllIllnesses() {
        return illnessRepository.findAll().stream()
                .map(IllnessConverter::toDto)
                .toList();
    }
}
