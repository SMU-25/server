package final_project.momeasy.domain.notification.controller;

import final_project.momeasy.domain.notification.dto.NotificationResponse;
import final_project.momeasy.domain.notification.service.NotificationService;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.apiPayload.CursorResponse;
import final_project.momeasy.global.security.annotation.AuthParent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Notification", description = "공지 API by 정현")
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "부모의 알림 목록 조회 (커서 기반 페이지네이션)")
    @GetMapping
    public CustomResponse<CursorResponse<NotificationResponse>> getNotifications(
            @AuthParent Parent parent,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "20") int size) {

        CursorResponse<NotificationResponse> notifications =
                notificationService.getNotifications(parent, cursor, size);

        return CustomResponse.onSuccess(notifications);
    }

    @Operation(summary = "알림 읽음 처리")
    @PatchMapping("/{notificationId}/read")
    public CustomResponse<Void> markAsRead(
            @AuthParent Parent parent,
            @PathVariable Long notificationId) {

        notificationService.markAsRead(parent, notificationId);
        return CustomResponse.onSuccess(null);
    }
}
