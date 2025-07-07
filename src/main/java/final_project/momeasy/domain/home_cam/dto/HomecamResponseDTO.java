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
        private Long childId;
        private String childName;
        private String serialNum;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    public static class HomecamDetailDTO {
        private String name;
        private String place;
        private Long childId;
        private String childName;
        private String serialNum;
        private String video_url;
        private LocalDateTime createdAt;
    }
}
