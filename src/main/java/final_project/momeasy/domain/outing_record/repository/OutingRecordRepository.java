package final_project.momeasy.domain.outing_record.repository;

import final_project.momeasy.domain.outing_record.entity.OutingRecord;
import final_project.momeasy.domain.room_condition.entity.RoomCondition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OutingRecordRepository extends JpaRepository<OutingRecord, Long> {
    Optional<OutingRecord> findTopByChildIdOrderByIdDesc(Long childId);
    Slice<OutingRecord> findAllByChildIdOrderByIdDesc(Long childId,Pageable pageable);

    @Transactional
    @Modifying
    @Query("DELETE FROM OutingRecord or WHERE or.createdAt < :time") //스케줄링(@Scheduled)과 함께 사용, 30일 분량의 데이터만 저장
    void deleteByCreatedAtBefore(@Param("time") LocalDateTime time);
}
