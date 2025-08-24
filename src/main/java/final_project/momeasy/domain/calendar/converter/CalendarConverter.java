package final_project.momeasy.domain.calendar.converter;

import final_project.momeasy.domain.calendar.dto.CalendarRequestDto;
import final_project.momeasy.domain.calendar.dto.CalendarResponseDto;
import final_project.momeasy.domain.calendar.entity.Calendar;
import final_project.momeasy.domain.parent.entity.Parent;
import org.springframework.stereotype.Component;

@Component
public class CalendarConverter {

    public Calendar toEntity(CalendarRequestDto dto, Parent parent) {
        return Calendar.create(
                dto.getRecordDate(),
                dto.getScheduleDate(),
                dto.getTitle(),
                dto.getContent(),
                parent
        );
    }

    public void apply(Calendar target, CalendarRequestDto dto) {
        target.update(
                dto.getRecordDate(),
                dto.getScheduleDate(),
                dto.getTitle(),
                dto.getContent()
        );
    }

    public CalendarResponseDto toResponseDto(Calendar entity) {
        return CalendarResponseDto.builder()
                .calendarId(entity.getId())
                .recordDate(entity.getRecordDate())
                .scheduleDate(entity.getScheduleDate())
                .title(entity.getTitle())
                .content(entity.getContent())
                .build();
    }
}
