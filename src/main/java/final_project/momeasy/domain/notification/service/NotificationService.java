package final_project.momeasy.domain.notification.service;

import final_project.momeasy.domain.notification.dto.NotificationResponse;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.apiPayload.CursorResponse;

public interface NotificationService {

    // 커서 기반 알림 목록 조회
    CursorResponse<NotificationResponse> getNotifications(Parent parent, Long cursor, int size);

    void markAsRead(Parent parent, Long notificationId);


}
