package final_project.momeasy.domain.calendar.controller;

import final_project.momeasy.domain.calendar.dto.CalendarRequestDto;
import final_project.momeasy.domain.calendar.dto.CalendarResponseDto;
import final_project.momeasy.domain.calendar.service.CalendarService;
import final_project.momeasy.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calendars")
public class CalendarController {

    private final CalendarService calendarService;

    @PostMapping
    public ResponseEntity<CalendarResponseDto> createCalendar(
            @RequestBody @Valid CalendarRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long parentId = userDetails.getParent().getId();
        return ResponseEntity.ok(calendarService.createCalendar(requestDto, parentId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarResponseDto> getCalendar(@PathVariable Long id) {
        return ResponseEntity.ok(calendarService.getCalendar(id));
    }

    @GetMapping("/my")
    public ResponseEntity<List<CalendarResponseDto>> getMyCalendars(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long parentId = userDetails.getParent().getId();
        return ResponseEntity.ok(calendarService.getCalendarsByParent(parentId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalendarResponseDto> updateCalendar(
            @PathVariable Long id,
            @RequestBody @Valid CalendarRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long parentId = userDetails.getParent().getId();
        return ResponseEntity.ok(calendarService.updateCalendar(id, requestDto, parentId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalendar(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long parentId = userDetails.getParent().getId();
        calendarService.deleteCalendar(id, parentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<CalendarResponseDto>> searchCalendars(
            @RequestParam String keyword,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long parentId = userDetails.getParent().getId();
        return ResponseEntity.ok(calendarService.searchCalendars(parentId, keyword));
    }

    @GetMapping("/date")
    public ResponseEntity<List<CalendarResponseDto>> getCalendarsByDate(
            @RequestParam String date,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long parentId = userDetails.getParent().getId();
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(calendarService.getCalendarsByDate(parentId, localDate));
    }
}
