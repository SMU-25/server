package final_project.momeasy.domain.parent.repository;

import final_project.momeasy.domain.parent.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    Optional<Parent> findById(Long id);
    Optional<Parent> findByEmail(String email);

    @Query("SELECT p from Parent p WHERE p.id = :parentId AND p.deletedAt IS NULL")
    Optional<Parent> findByIdNotDeleted(@Param("parentId") Long id);

}
