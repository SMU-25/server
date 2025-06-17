package final_project.momeasy.domain.calendar.dto;

import final_project.momeasy.domain.calendar.entity.Calendar;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CalendarResponseDto {
    private Long id;
    private LocalDate scheduleDate;
    private String title;
    private String content;

    public static CalendarResponseDto fromEntity(Calendar calendar) {
        return CalendarResponseDto.builder()
                .id(calendar.getId())
                .scheduleDate(calendar.getScheduleDate())
                .title(calendar.getTitle())
                .content(calendar.getContent())
                .build();
    }
}
