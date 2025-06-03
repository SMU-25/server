package final_project.momeasy.domain.room_condition.repository;

import final_project.momeasy.domain.room_condition.entity.RoomCondition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RoomConditionRepository extends JpaRepository<RoomCondition, Long> {
    Optional<RoomCondition> findTopByChildIdOrderByIdDesc(Long childId);
    Slice<RoomCondition> findAllByChildIdOrderByIdDesc(Long childId, Pageable pageable);
    List<RoomCondition> findAllByChildIdOrderByIdDesc(Long childId);

    @Transactional
    @Modifying
    @Query("DELETE FROM RoomCondition rc WHERE rc.createdAt < :time") //스케줄링(@Scheduled)과 함께 사용, 30일 분량의 데이터만 저장
    void deleteByCreatedAtBefore(@Param("time") LocalDateTime time);

}
