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
        String password;
        String email;
        LocalDate birthdate;
        SocialType socialType;
        Gender gender;
    }
}
