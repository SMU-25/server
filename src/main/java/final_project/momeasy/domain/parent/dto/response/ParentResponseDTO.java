package final_project.momeasy.domain.parent.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ParentResponseDTO {

    @Builder
    @Getter
    public static class ParentCreateResponseDTO {
        Long id;
        LocalDateTime createdAt;
    }
}
