package final_project.momeasy.domain.home_cam.dto;

import lombok.*;

import java.time.LocalDateTime;

public class HomecamResponseDTO {
    @Getter
    @Builder
    public static class HomecamDTO {
        private Long homecamId;
        private String name;
        private String place;
        private LocalDateTime createdAt;
        private String serialNum;
    }

    @Getter
    @Builder
    public static class HomecamDetailDTO {
        private String name;
        private String place;
        private String video_url;
    }
}
