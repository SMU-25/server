package final_project.momeasy.domain.calendar.service;

import final_project.momeasy.domain.calendar.dto.CalendarRequestDto;
import final_project.momeasy.domain.calendar.dto.CalendarResponseDto;
import final_project.momeasy.domain.calendar.entity.Calendar;
import final_project.momeasy.domain.calendar.repository.CalendarRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;

    // 1. 일정 추가
    public CalendarResponseDto createCalendar(CalendarRequestDto requestDto, Parent parent) {
        Calendar calendar = Calendar.builder()
                .scheduleDate(requestDto.getScheduleDate())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .parent(parent)
                .build();

        Calendar saved = calendarRepository.save(calendar);
        return CalendarResponseDto.fromEntity(saved);
    }

    // 2. 일정 단건 조회
    public CalendarResponseDto getCalendar(Long id) {
        Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 일정을 찾을 수 없습니다."));
        return CalendarResponseDto.fromEntity(calendar);
    }

    // 3. 로그인한 사용자 기준 전체 일정 조회
    public List<CalendarResponseDto> getCalendarsByParent(Parent parent) {
        return calendarRepository.findByParent(parent).stream()
                .map(CalendarResponseDto::fromEntity)
                .toList();
    }

    // 4. 일정 수정
    public CalendarResponseDto updateCalendar(Long id, CalendarRequestDto requestDto, Parent parent) {
        Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 일정을 찾을 수 없습니다."));

        if (!calendar.getParent().getId().equals(parent.getId())) {
            throw new SecurityException("해당 일정에 대한 수정 권한이 없습니다.");
        }

        calendar.update(
                requestDto.getScheduleDate(),
                requestDto.getTitle(),
                requestDto.getContent()
        );

        calendarRepository.save(calendar);

        return CalendarResponseDto.fromEntity(calendar);
    }

    // 5. 일정 삭제
    public void deleteCalendar(Long id, Parent parent) {
        Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 일정을 찾을 수 없습니다."));

        if (!calendar.getParent().getId().equals(parent.getId())) {
            throw new SecurityException("해당 일정에 대한 삭제 권한이 없습니다.");
        }

        calendarRepository.delete(calendar);
    }

    // 6. 날짜 + 로그인 사용자 기준 일정 조회
    public List<CalendarResponseDto> getCalendarsByDate(Parent parent, LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        List<Calendar> calendars = calendarRepository.findByYearMonthDayAndParentId(year, month, day, parent.getId());
        return calendars.stream()
                .map(CalendarResponseDto::fromEntity)
                .toList();
    }

    // 7. 제목 검색 + 로그인 사용자 기준 일정 조회
    public List<CalendarResponseDto> searchCalendars(Parent parent, String keyword) {
        List<Calendar> calendars = calendarRepository.findByTitleContainingIgnoreCase(keyword).stream()
                .filter(c -> c.getParent().getId().equals(parent.getId()))
                .toList();

        return calendars.stream()
                .map(CalendarResponseDto::fromEntity)
                .toList();
    }
}
