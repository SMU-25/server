package final_project.momeasy.domain.notification.repository;

import final_project.momeasy.domain.notification.entity.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // 커서가 없는 경우 (최신순 전체 페이지 첫 요청)
    List<Notification> findByParentIdOrderByIdDesc(Long parentId, Pageable pageable);

    // 커서가 있는 경우 (이전 ID보다 작은 데이터만 조회)
    List<Notification> findByParentIdAndIdLessThanOrderByIdDesc(Long parentId, Long cursorId, Pageable pageable);

    // 기존 용도로 남겨두는 거면 유지, 아니라면 삭제 가능
    Optional<Notification> findTopByParentIdAndChildIdOrderByIdDesc(long parentId, long childId);
}
