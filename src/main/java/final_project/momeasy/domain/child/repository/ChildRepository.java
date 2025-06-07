package final_project.momeasy.domain.child.repository;

import final_project.momeasy.domain.child.entity.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child, Long> {
    @Query("SELECT c FROM Child c WHERE c.id = :id AND c.deletedAt IS NULL")
    Optional<Child> findById(@Param("id") Long id);

    @Query("SELECT c FROM Child c JOIN FETCH ParentChild pc ON c.id = pc.child.id WHERE pc.parent.id = :parentId AND c.deletedAt IS NULL")
    List<Child> findByParentId(@Param("parentId") Long parentId);

    @Query("SELECT c FROM Child c JOIN FETCH c.homecam WHERE c.homecam IS NOT NULL AND c.deletedAt IS NULL")
    List<Child> findByHomecamIsNotNull();

    @Query("""
            SELECT COUNT(c) > 0 FROM Child c
            JOIN ParentChild pc ON pc.child.id = c.id
            WHERE c.id = :childId AND pc.parent.id = :parentId
                                  AND c.deletedAt IS NULL""")
    boolean existsByChildIdAndParentId(@Param("childId") Long childId, @Param("parentId") Long parentId);
}
