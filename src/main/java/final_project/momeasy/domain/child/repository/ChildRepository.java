package final_project.momeasy.domain.child.repository;

import final_project.momeasy.domain.child.entity.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child, Long> {
    Optional<Child> findById(Long id);

    @Query("SELECT c FROM Child c JOIN FETCH ParentChild pc ON c.id = pc.child.id WHERE pc.parent.id = :parentId")
    List<Child> findByParentId(@Param("parentId") Long parentId);

    @Query("SELECT c FROM Child c JOIN FETCH c.homecam WHERE c.homecam IS NOT NULL")
    List<Child> findByHomecamIsNotNull();
}
