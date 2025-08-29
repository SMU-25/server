package final_project.momeasy.domain.announcement.repository;

import final_project.momeasy.domain.announcement.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long>, JpaSpecificationExecutor<Announcement> {
    Optional<Announcement> findByIdAndDeletedFalse(Long id);
}
