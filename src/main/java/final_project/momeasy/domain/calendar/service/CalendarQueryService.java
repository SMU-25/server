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

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarQueryService {

    private final CalendarRepository calendarRepository;

    // 1. 일정 단건 조회
    public CalendarResponseDto getCalendar(Long id) {
        Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(() -> new CalendarException(CalendarErrorCode.NOT_FOUND));
        return CalendarConverter.toResponseDto(calendar);
    }

    // 2. 내 전체 일정 조회
    public List<CalendarResponseDto> getCalendarsByParent(Parent parent) {
        return calendarRepository.findByParent(parent).stream()
                .map(CalendarConverter::toResponseDto)
                .toList();
    }

    // 3. 날짜 기반 일정 조회
    public List<CalendarResponseDto> getCalendarsByDate(Parent parent, LocalDate date) {
        List<Calendar> calendars = calendarRepository.findByScheduleDateAndParent(date, parent);
        return calendars.stream()
                .map(CalendarConverter::toResponseDto)
                .toList();
    }

    // 4. 키워드 기반 일정 검색
    public List<CalendarResponseDto> searchCalendars(Parent parent, String keyword) {
        List<Calendar> calendars = calendarRepository.findByTitleContainingIgnoreCase(keyword).stream()
                .filter(c -> c.getParent().getId().equals(parent.getId()))
                .toList();

        if (calendars.isEmpty()) {
            throw new CalendarException(CalendarErrorCode.SEARCH_NO_RESULT);
        }

        return calendars.stream()
                .map(CalendarConverter::toResponseDto)
                .toList();
    }
}
