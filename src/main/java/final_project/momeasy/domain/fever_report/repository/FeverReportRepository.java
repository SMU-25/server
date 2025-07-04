package final_project.momeasy.domain.fever_report.repository;

import final_project.momeasy.domain.fever_report.entity.FeverReport;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FeverReportRepository extends JpaRepository<FeverReport, Long> {
    Optional<FeverReport> findById(Long id);

    @Query("SELECT fr FROM FeverReport fr JOIN FETCH fr.child c WHERE c.id =:childId AND fr.id <:cursor ORDER BY fr.id DESC")
    Slice<FeverReport> findFeverReportCursorPagination(Long childId, Long cursor, Pageable pageable);

    List<FeverReport> findAllByChildIdOrderByIdDesc(Long ChildId);
}
