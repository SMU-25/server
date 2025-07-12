package final_project.momeasy.domain.child.repository;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.child.entity.ChildIllness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChildIllnessRepository extends JpaRepository<ChildIllness, Long> {

    @Modifying
    @Query("DELETE FROM ChildIllness ci WHERE ci.child = :child")
    void deleteByChild(@Param("child")Child child);
}
