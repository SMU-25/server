package final_project.momeasy.domain.calendar.controller;

import final_project.momeasy.domain.calendar.dto.CalendarRequestDto;
import final_project.momeasy.domain.calendar.dto.CalendarResponseDto;
import final_project.momeasy.domain.calendar.service.CalendarService;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.annotation.AuthParent;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calendars")
public class CalendarController {

    private final CalendarService calendarService;

    @Operation(summary = "캘린더 일정 생성")
    @PostMapping
    public CustomResponse<CalendarResponseDto> createCalendar(
            @RequestBody @Valid CalendarRequestDto requestDto,
            @AuthParent Parent parent) {
        CalendarResponseDto response = calendarService.createCalendar(requestDto, parent);
        return CustomResponse.onSuccess(HttpStatus.CREATED, response);
    }

    @Operation(summary = "캘린더 일정 단건 조회")
    @GetMapping("/{calendarId}")
    public CustomResponse<CalendarResponseDto> getCalendar(@PathVariable Long calendarId) {
        CalendarResponseDto response = calendarService.getCalendar(calendarId);
        return CustomResponse.onSuccess(response);
    }

    @Operation(summary = "내 캘린더 일정 전체 조회")
    @GetMapping("/my")
    public CustomResponse<List<CalendarResponseDto>> getMyCalendars(
            @AuthParent Parent parent) {
        List<CalendarResponseDto> response = calendarService.getCalendarsByParent(parent);
        return CustomResponse.onSuccess(response);
    }

    @Operation(summary = "캘린더 일정 수정")
    @PutMapping("/{calendarId}")
    public CustomResponse<CalendarResponseDto> updateCalendar(
            @PathVariable Long calendarId,
            @RequestBody @Valid CalendarRequestDto requestDto,
            @AuthParent Parent parent) {
        CalendarResponseDto response = calendarService.updateCalendar(calendarId, requestDto, parent);
        return CustomResponse.onSuccess(response);
    }

    @Operation(summary = "캘린더 일정 삭제")
    @DeleteMapping("/{calendarId}")
    public CustomResponse<Void> deleteCalendar(
            @PathVariable Long calendarId,
            @AuthParent Parent parent) {
        calendarService.deleteCalendar(calendarId, parent);
        return CustomResponse.onSuccess(HttpStatus.NO_CONTENT, null);
    }

    @Operation(summary = "키워드로 일정 검색")
    @GetMapping("/search")
    public CustomResponse<List<CalendarResponseDto>> searchCalendars(
            @RequestParam String keyword,
            @AuthParent Parent parent) {
        List<CalendarResponseDto> response = calendarService.searchCalendars(parent, keyword);
        return CustomResponse.onSuccess(response);
    }

    @Operation(summary = "날짜로 일정 조회")
    @GetMapping("/date")
    public CustomResponse<List<CalendarResponseDto>> getCalendarsByDate(
            @RequestParam String date,
            @AuthParent Parent parent) {
        LocalDate localDate = LocalDate.parse(date);
        List<CalendarResponseDto> response = calendarService.getCalendarsByDate(parent, localDate);
        return CustomResponse.onSuccess(response);
    }
}
