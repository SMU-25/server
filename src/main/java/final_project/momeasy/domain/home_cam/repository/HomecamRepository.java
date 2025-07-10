package final_project.momeasy.domain.home_cam.repository;

import final_project.momeasy.domain.home_cam.entity.Homecam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HomecamRepository extends JpaRepository<Homecam, Long> {
    @Query("select h from Homecam h where h.child.deletedAt is null and h.parent.id =:parentId order by h.id DESC")
    List<Homecam> findAllByParentIdOrderByIdDesc(Long parentId);

    @Query("select h from Homecam h where h.id =:HomecamId and h.child.deletedAt is null")
    Optional<Homecam> findById(Long HomecamId);

    @Query("select h from Homecam h where h.child.id =:childId and h.child.deletedAt is null")
    Optional<Homecam> findByChildId(Long childId);

    @Query("select h from Homecam h where h.serialNum =: serial_num and h.child.deletedAt is null")
    Optional<Homecam> findBySerialNum(String serial_num);
}
