package final_project.momeasy.domain.notification.service;

import final_project.momeasy.domain.notification.dto.NotificationRequest;
import final_project.momeasy.domain.notification.dto.NotificationResponse;
import final_project.momeasy.domain.parent.entity.Parent;

import java.util.List;

public interface NotificationService {

    List<NotificationResponse> getNotifications(Parent parent);

    void markAsRead(Parent parent, Long notificationId);

    void createNotification(Parent parent, NotificationRequest request);
}
