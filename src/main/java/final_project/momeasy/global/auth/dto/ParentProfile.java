package final_project.momeasy.global.auth.dto;

import final_project.momeasy.common.enums.Gender;
import final_project.momeasy.common.enums.SocialType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ParentProfile {
    private String name;

    private String email;

    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthdate;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;
}
