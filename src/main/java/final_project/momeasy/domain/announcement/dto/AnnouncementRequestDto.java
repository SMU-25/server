package final_project.momeasy.domain.announcement.dto;

import final_project.momeasy.common.enums.AnnouncementType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnnouncementRequestDto {
    private AnnouncementType type;
    private String title;
    private String content;
    private Boolean pinned;   // null이면 변경 없음
}
