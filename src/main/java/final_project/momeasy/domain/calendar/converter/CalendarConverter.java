package final_project.momeasy.domain.calendar.converter;

import final_project.momeasy.domain.calendar.dto.CalendarRequestDto;
import final_project.momeasy.domain.calendar.dto.CalendarResponseDto;
import final_project.momeasy.domain.calendar.entity.Calendar;
import final_project.momeasy.domain.parent.entity.Parent;

public class CalendarConverter {

    public static CalendarResponseDto toResponseDto(Calendar calendar) {
        return CalendarResponseDto.builder()
                .calendarId(calendar.getId())
                .recordDate(calendar.getRecordDate())
                .title(calendar.getTitle())
                .content(calendar.getContent())
                .build();
    }

    public static Calendar toEntity(CalendarRequestDto dto, Parent parent) {
        return Calendar.builder()
                .recordDate(dto.getRecordDate())
                .title(dto.getTitle())
                .content(dto.getContent())
                .parent(parent)
                .build();
    }
}
