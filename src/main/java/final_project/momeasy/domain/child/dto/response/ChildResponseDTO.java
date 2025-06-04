package final_project.momeasy.domain.child.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

public class ChildResponseDTO {

    @Builder
    public record ChildCreateResponseDTO(
            Long id,
            LocalDateTime createdAt
    ) {
    }
}
