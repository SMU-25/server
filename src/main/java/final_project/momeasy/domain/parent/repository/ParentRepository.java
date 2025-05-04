package final_project.momeasy.domain.parent.repository;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.setting.entity.Setting;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    Optional<Parent> findById(Long id);
    Optional<Parent> findByEmail(String email);

}
