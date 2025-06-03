package final_project.momeasy.domain.fever_record.repository;

import final_project.momeasy.domain.fever_record.entity.FeverRecord;
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

public interface FeverRecordRepository extends JpaRepository<FeverRecord, Long> {
    Optional<FeverRecord> findTopByChildIdOrderByIdDesc(Long childId);
    Slice<FeverRecord> findAllByChildIdOrderByIdDesc(Long childId, Pageable pageable);
    List<FeverRecord> findAllByChildIdOrderByIdDesc(Long childId);

    @Query("SELECT fr FROM FeverRecord fr WHERE fr.child.id  = :childId AND fr.fever>=37.5 ORDER BY fr.createdAt DESC LIMIT 1")
    Optional<FeverRecord> findRecentFeverRecord(Long childId);

    @Transactional
    @Modifying
    @Query("DELETE FROM FeverRecord fr WHERE fr.createdAt < :time") //스케줄링(@Scheduled)과 함께 사용, 7일 분량의 데이터만 저장
    void deleteByCreatedAtBefore(@Param("time") LocalDateTime time);
}
