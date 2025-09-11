package final_project.momeasy.domain.announcement.controller;

import final_project.momeasy.common.enums.AnnouncementType;
import final_project.momeasy.domain.announcement.dto.AnnouncementRequestDto;
import final_project.momeasy.domain.announcement.dto.AnnouncementResponseDto;
import final_project.momeasy.domain.announcement.service.AnnouncementService;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.annotation.AuthParent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
@Tag(name = "Announcement", description = "공지/이벤트 API by 정현")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @Operation(summary = "공지/이벤트 생성")
    @PostMapping
    public CustomResponse<AnnouncementResponseDto> create(
            @AuthParent Parent parent,
            @RequestBody AnnouncementRequestDto request
    ) {
        return CustomResponse.onSuccess(HttpStatus.OK,
                announcementService.create(parent, request));
    }

    @Operation(summary = "공지/이벤트 단건 조회")
    @GetMapping("/{announcementId}")
    public CustomResponse<AnnouncementResponseDto> get(@PathVariable Long announcementId) {
        return CustomResponse.onSuccess(HttpStatus.OK,
                announcementService.get(announcementId));
    }

    @Operation(summary = "공지/이벤트 목록 조회(페이지네이션)")
    @GetMapping
    public CustomResponse<Page<AnnouncementResponseDto>> list(
            @RequestParam(required = false) AnnouncementType type,
            @ParameterObject Pageable pageable
    ) {
        return CustomResponse.onSuccess(HttpStatus.OK,
                announcementService.list(type, pageable));
    }

    @Operation(summary = "공지/이벤트 수정")
    @PatchMapping("/{announcementId}")
    public CustomResponse<AnnouncementResponseDto> update(
            @AuthParent Parent parent,
            @PathVariable Long announcementId,
            @RequestBody AnnouncementRequestDto request
    ) {
        return CustomResponse.onSuccess(HttpStatus.OK,
                announcementService.update(parent, announcementId, request));
    }

    @Operation(summary = "공지/이벤트 삭제(소프트)")
    @DeleteMapping("/{announcementId}")
    public CustomResponse<Void> delete(
            @AuthParent Parent parent,
            @PathVariable Long announcementId
    ) {
        announcementService.delete(parent, announcementId);
        return CustomResponse.onSuccess(HttpStatus.OK, null);
    }

    // 공지 푸시 (무조건 notice 토픽)
    @Operation(summary = "공지 푸시 발송")
    @PostMapping("/{announcementId}/push-notice")
    public CustomResponse<Void> pushNotice(
            @AuthParent Parent parent,
            @PathVariable Long announcementId
    ) {
        announcementService.pushNotice(announcementId);
        return CustomResponse.onSuccess(HttpStatus.OK, null);
    }

    // 이벤트 푸시 (마케팅 동의자만 수신)
    @Operation(summary = "이벤트 푸시 발송")
    @PostMapping("/{announcementId}/push-event")
    public CustomResponse<Void> pushEvent(
            @AuthParent Parent parent,
            @PathVariable Long announcementId
    ) {
        announcementService.pushEvent(announcementId);
        return CustomResponse.onSuccess(HttpStatus.OK, null);
    }
}
