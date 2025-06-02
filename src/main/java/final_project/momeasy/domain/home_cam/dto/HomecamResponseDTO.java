package final_project.momeasy.domain.home_cam.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HomecamResponseDTO {
    @Getter
    @Builder
    public static class HomecamDTO {
        private String name;
        private String place;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    public static class HomecamVideoDTO {
        private String video_url;
    }
}
