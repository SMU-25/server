package final_project.momeasy.domain.notification.repository;

import final_project.momeasy.domain.notification.entity.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Slice<Notification> findByParentId(long parentId, Pageable pageable);
    Optional<Notification> findTopByParentIdAndChildIdOrderByIdDesc(long parentId, long childId);
}
