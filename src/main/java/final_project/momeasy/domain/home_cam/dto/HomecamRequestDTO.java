package final_project.momeasy.domain.home_cam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class HomecamRequestDTO {
    @Getter
    @Builder
    public static class HomecamCreateDTO {
        @NotNull(message = "홈캠 번호는 필수 입력 값입니다.")
        @JsonProperty("serial_num")
        private String serial_num;

        @NotBlank(message = "홈캠 이름은 필수 입력 값입니다.")
        @JsonProperty("name")
        private String name;

        @NotBlank(message = "설치 장소는 필수 입력 값입니다.")
        @JsonProperty("place")
        private String place;

        @NotBlank(message = "아이는 필수 입력 값입니다.")
        private Long childId;
    }

    @Getter
    @Builder
    public static class HomecamUpdateDTO {
        @NotBlank(message = "홈캠 이름은 필수 입력 값입니다.")
        @JsonProperty("name")
        private String name;

        @NotBlank(message = "설치 장소는 필수 입력 값입니다.")
        @JsonProperty("place")
        private String place;

        @NotBlank(message = "아이는 필수 입력 값입니다.")
        private Long childId;

        @NotNull(message = "홈캠 번호는 필수 입력 값입니다.")
        @JsonProperty("serial_num")
        private String serial_num;
    }
}
