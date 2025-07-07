package final_project.momeasy.domain.illness.repository;

import final_project.momeasy.domain.illness.entity.Illness;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IllnessRepository extends JpaRepository<Illness, Long> {
}
