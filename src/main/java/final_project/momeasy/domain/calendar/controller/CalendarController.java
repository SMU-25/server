package final_project.momeasy.domain.calendar.controller;

import final_project.momeasy.domain.calendar.dto.CalendarRequestDto;
import final_project.momeasy.domain.calendar.dto.CalendarResponseDto;
import final_project.momeasy.domain.calendar.service.CalendarService;
import final_project.momeasy.domain.calendar.service.CalendarQueryService;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.annotation.AuthParent;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Calendar", description = "캘린더 API by 정현")
@RequestMapping("/api/calendars")
public class CalendarController {

    private final CalendarService calendarService;
    private final CalendarQueryService calendarQueryService;

    @Operation(summary = "캘린더 일정 생성")
    @PostMapping
    public CustomResponse<CalendarResponseDto> createCalendar(
            @RequestBody @Valid CalendarRequestDto requestDto,
            @AuthParent Parent parent) {
        var response = calendarService.createCalendar(requestDto, parent);
        // 컨벤션: 성공은 OK 사용
        return CustomResponse.onSuccess(HttpStatus.OK, response);
    }

    @Operation(summary = "캘린더 일정 단건 조회")
    @GetMapping("/{calendarId}")
    public CustomResponse<CalendarResponseDto> getCalendar(
            @PathVariable Long calendarId,
            @AuthParent Parent parent) {
        var response = calendarQueryService.getCalendar(calendarId, parent);
        return CustomResponse.onSuccess(response);
    }

    @Operation(summary = "내 캘린더 일정 전체 조회")
    @GetMapping("/my")
    public CustomResponse<List<CalendarResponseDto>> getMyCalendars(
            @AuthParent Parent parent) {
        var response = calendarQueryService.getCalendarsByParent(parent);
        return CustomResponse.onSuccess(response);
    }

    @Operation(summary = "캘린더 일정 수정")
    @PutMapping("/{calendarId}")
    public CustomResponse<CalendarResponseDto> updateCalendar(
            @PathVariable Long calendarId,
            @RequestBody @Valid CalendarRequestDto requestDto,
            @AuthParent Parent parent) {
        var response = calendarService.updateCalendar(calendarId, requestDto, parent);
        return CustomResponse.onSuccess(response);
    }

    @Operation(summary = "캘린더 일정 삭제")
    @DeleteMapping("/{calendarId}")
    public CustomResponse<String> deleteCalendar(
            @PathVariable Long calendarId,
            @AuthParent Parent parent) {
        calendarService.deleteCalendar(calendarId, parent);
        return CustomResponse.onSuccess(HttpStatus.OK, "캘린더 일정이 삭제되었습니다.");
    }

    @Operation(summary = "키워드로 일정 검색")
    @GetMapping("/search")
    public CustomResponse<List<CalendarResponseDto>> searchCalendars(
            @RequestParam String keyword,
            @AuthParent Parent parent) {
        var response = calendarQueryService.searchCalendars(parent, keyword);
        return CustomResponse.onSuccess(response);
    }

    @Operation(summary = "일정 날짜(scheduleDate)로 일정 조회")
    @GetMapping("/schedule-date")
    public CustomResponse<List<CalendarResponseDto>> getCalendarsByScheduleDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @AuthParent Parent parent) {
        var response = calendarQueryService.getCalendarsByScheduleDate(parent, date);
        return CustomResponse.onSuccess(response);
    }
}
