package final_project.momeasy.domain.home_cam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class HomecamRequestDTO {
    @Getter
    @Builder
    public static class HomecamRegisterDTO {
        @NotNull(message = "기기 번호는 필수 입력 값입니다.")
        @JsonProperty("serial_num")
        private String serial_num;

        @NotBlank(message = "기기 이름은 필수 입력 값입니다.")
        @JsonProperty("name")
        private String name;

        @NotBlank(message = "설치 장소는 필수 입력 값입니다.")
        @JsonProperty("place")
        private String place;
    }
}
