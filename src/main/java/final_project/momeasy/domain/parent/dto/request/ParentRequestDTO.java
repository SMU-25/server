package final_project.momeasy.domain.parent.dto.request;

import final_project.momeasy.common.enums.Gender;
import final_project.momeasy.common.enums.SocialType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;


public class ParentRequestDTO {

    @Builder
    @Getter
    public static class ParentCreateRequestDTO {
        String name;
        String email;
        String password;
        LocalDate birthdate;
        SocialType socialType;
        Gender gender;
    }

    @Builder
    public record ParentUpdateRequestDTO(
            String name,
            LocalDate birthdate,
            Gender gender,
            String newPassword
    ) {
    }
}
