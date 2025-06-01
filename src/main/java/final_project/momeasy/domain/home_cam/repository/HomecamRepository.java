package final_project.momeasy.domain.home_cam.repository;

import final_project.momeasy.domain.home_cam.entity.Homecam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HomecamRepository extends JpaRepository<Homecam, Long> {
    List<Homecam> findAllByParentId(Long parentId);
    Optional<Homecam> findById(Long HomecamId);
}
