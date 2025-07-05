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
public class CalendarService {

    private final CalendarRepository calendarRepository;

    // 1. 일정 추가
    @Transactional
    public CalendarResponseDto createCalendar(CalendarRequestDto requestDto, Parent parent) {
        Calendar calendar = CalendarConverter.toEntity(requestDto, parent);
        Calendar saved = calendarRepository.save(calendar);
        return CalendarConverter.toResponseDto(saved);
    }

    // 2. 일정 수정
    @Transactional
    public CalendarResponseDto updateCalendar(Long id, CalendarRequestDto requestDto, Parent parent) {
        Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(() -> new CalendarException(CalendarErrorCode.CALENDAR_NOT_FOUND));

        if (!calendar.getParent().getId().equals(parent.getId())) {
            throw new CalendarException(CalendarErrorCode.NO_CALENDAR_ACCESS);
        }

        calendar.update(
                requestDto.getScheduleDate(),
                requestDto.getTitle(),
                requestDto.getContent()
        );

        calendarRepository.save(calendar);

        return CalendarConverter.toResponseDto(calendar);
    }

    // 3. 일정 삭제
    @Transactional
    public void deleteCalendar(Long id, Parent parent) {
        Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(() -> new CalendarException(CalendarErrorCode.CALENDAR_NOT_FOUND));

        if (!calendar.getParent().getId().equals(parent.getId())) {
            throw new CalendarException(CalendarErrorCode.NO_CALENDAR_ACCESS);
        }

        calendarRepository.delete(calendar);
    }
}
