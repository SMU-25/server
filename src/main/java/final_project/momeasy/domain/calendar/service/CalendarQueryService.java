package final_project.momeasy.domain.calendar.service;

import final_project.momeasy.domain.calendar.converter.CalendarConverter;
import final_project.momeasy.domain.calendar.dto.CalendarResponseDto;
import final_project.momeasy.domain.calendar.entity.Calendar;
import final_project.momeasy.domain.calendar.exception.CalendarErrorCode;
import final_project.momeasy.domain.calendar.exception.CalendarException;
import final_project.momeasy.domain.calendar.repository.CalendarRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CalendarQueryService {

    private final CalendarRepository calendarRepository;
    private final CalendarConverter converter;

    public CalendarResponseDto getCalendar(Long id, Parent parent) {
        Calendar calendar = calendarRepository.findByIdAndParent(id, parent)
                .orElseThrow(() -> new CalendarException(CalendarErrorCode.NOT_FOUND));
        return converter.toResponseDto(calendar);
    }

    public List<CalendarResponseDto> getCalendarsByParent(Parent parent) {
        return calendarRepository.findByParent(parent).stream()
                .map(converter::toResponseDto)
                .toList();
    }

    public List<CalendarResponseDto> getCalendarsByRecordDate(Parent parent, LocalDate date) {
        return calendarRepository.findByRecordDateAndParent(date, parent).stream()
                .map(converter::toResponseDto)
                .toList();
    }

    public List<CalendarResponseDto> getCalendarsByScheduleDate(Parent parent, LocalDate scheduleDate) {
        return calendarRepository.findByScheduleDateAndParent(scheduleDate, parent).stream()
                .map(converter::toResponseDto)
                .toList();
    }

    public List<CalendarResponseDto> searchCalendars(Parent parent, String keyword) {
        var calendars = calendarRepository.findByTitleContainingIgnoreCaseAndParent(keyword, parent);
        if (calendars.isEmpty()) {
            throw new CalendarException(CalendarErrorCode.SEARCH_NO_RESULT);
        }
        return calendars.stream().map(converter::toResponseDto).toList();
    }
}
