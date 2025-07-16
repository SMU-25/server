package final_project.momeasy.domain.calendar.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CalendarResponseDto {
    private Long calendarId;
    private LocalDate recordDate;
    private String title;
    private String content;
}
