package final_project.momeasy.domain.notification.repository;

import final_project.momeasy.domain.notification.entity.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Slice<Notification> findByParentId(long parentid, Pageable pageable);
    Optional<Notification> findTopByParentIdAndChildIdOrderByIdDesc(long parentid, long childid);
}
