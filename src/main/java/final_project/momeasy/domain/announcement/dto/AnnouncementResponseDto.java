package final_project.momeasy.domain.announcement.dto;

import final_project.momeasy.common.enums.AnnouncementType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AnnouncementResponseDto {
    private Long id;
    private AnnouncementType type;
    private String title;
    private String content;
    private boolean pinned;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
