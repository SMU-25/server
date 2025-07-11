package final_project.momeasy.domain.notification.controller;

import final_project.momeasy.domain.notification.dto.NotificationRequest;
import final_project.momeasy.domain.notification.dto.NotificationResponse;
import final_project.momeasy.domain.notification.service.NotificationService;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.annotation.AuthParent;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "부모의 알림 목록 조회")
    @GetMapping
    public CustomResponse<List<NotificationResponse>> getNotifications(@AuthParent Parent parent) {
        List<NotificationResponse> notifications = notificationService.getNotifications(parent);
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

    @Operation(summary = "알림 생성")
    @PostMapping
    public CustomResponse<Void> createNotification(
            @AuthParent Parent parent,
            @RequestBody NotificationRequest request) {
        notificationService.createNotification(parent, request);
        return CustomResponse.onSuccess(null);
    }
}
