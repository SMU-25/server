package final_project.momeasy.domain.calendar.service;

import final_project.momeasy.domain.calendar.converter.CalendarConverter;
import final_project.momeasy.domain.calendar.dto.CalendarRequestDto;
import final_project.momeasy.domain.calendar.dto.CalendarResponseDto;
import final_project.momeasy.domain.calendar.entity.Calendar;
import final_project.momeasy.domain.calendar.exception.CalendarErrorCode;
import final_project.momeasy.domain.calendar.exception.CalendarException;
import final_project.momeasy.domain.calendar.repository.CalendarRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional // 클래스 상단 트랜잭션(쓰기)
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final CalendarConverter converter;

    public CalendarResponseDto createCalendar(CalendarRequestDto requestDto, Parent parent) {
        Calendar saved = calendarRepository.save(converter.toEntity(requestDto, parent));
        return converter.toResponseDto(saved);
    }

    public CalendarResponseDto updateCalendar(Long id, CalendarRequestDto requestDto, Parent parent) {
        Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(() -> new CalendarException(CalendarErrorCode.NOT_FOUND));

        if (!calendar.getParent().getId().equals(parent.getId())) {
            throw new CalendarException(CalendarErrorCode.NO_CALENDAR_ACCESS);
        }

        converter.apply(calendar, requestDto); // 더티체킹
        return converter.toResponseDto(calendar);
    }

    public void deleteCalendar(Long id, Parent parent) {
        Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(() -> new CalendarException(CalendarErrorCode.NOT_FOUND));

        if (!calendar.getParent().getId().equals(parent.getId())) {
            throw new CalendarException(CalendarErrorCode.NO_CALENDAR_ACCESS);
        }

        calendarRepository.delete(calendar);
    }
}
