package final_project.momeasy.domain.parent.dto.response;

import final_project.momeasy.common.enums.Gender;
import final_project.momeasy.common.enums.SocialType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ParentResponseDTO {

    @Builder
    @Getter
    public static class ParentCreateResponseDTO {
        Long id;
        LocalDateTime createdAt;
    }

    @Builder
    public record ParentDetailResponseDTO(
            String name,
            String email,
            String password,
            LocalDate birthdate,
            Gender gender,
            SocialType socialType
    ) {
    }
}
