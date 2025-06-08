package final_project.momeasy.domain.illness.repository;


import final_project.momeasy.common.enums.IllnessType;
import final_project.momeasy.domain.illness.entity.Illness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IllnessRepository extends JpaRepository<Illness, Long> {

    @Query("select i from Illness i join fetch ChildIllness ci on i.id = ci.illness.id where ci.child.id = :childId")
    List<Illness> findByChildId(@Param("childId") Long childId);

    Optional<Illness> findByIllnessType(IllnessType illnessType);
}
