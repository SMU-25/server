package final_project.momeasy.domain.calendar.converter;

import final_project.momeasy.domain.calendar.dto.CalendarRequestDto;
import final_project.momeasy.domain.calendar.dto.CalendarResponseDto;
import final_project.momeasy.domain.calendar.entity.Calendar;
import final_project.momeasy.domain.parent.entity.Parent;

public class CalendarConverter {

    public static CalendarResponseDto toResponseDto(Calendar calendar) {
        return CalendarResponseDto.builder()
                .calendarId(calendar.getId())
                .scheduleDate(calendar.getScheduleDate())
                .title(calendar.getTitle())
                .content(calendar.getContent())
                .build();
    }

    public static Calendar toEntity(CalendarRequestDto dto, Parent parent) {
        return Calendar.builder()
                .scheduleDate(dto.getScheduleDate())
                .title(dto.getTitle())
                .content(dto.getContent())
                .parent(parent)
                .build();
    }
}
