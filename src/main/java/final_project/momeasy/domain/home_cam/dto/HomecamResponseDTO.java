package final_project.momeasy.domain.home_cam.dto;

import lombok.*;

public class HomecamResponseDTO {
    @Getter
    @Builder
    public static class HomecamDTO {
        private String name;
        private String place;
        private String date;
    }

    @Getter
    @Builder
    public static class HomecamVideoDTO {
        private String video_url;
    }
}
