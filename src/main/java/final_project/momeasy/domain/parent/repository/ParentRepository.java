package final_project.momeasy.domain.parent.repository;

import final_project.momeasy.domain.parent.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    Optional<Parent> findById(Long id);
    Optional<Parent> findByEmail(String email);

}
