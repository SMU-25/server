package final_project.momeasy.domain.fever_report.repository;

import final_project.momeasy.domain.fever_report.entity.FeverReport;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FeverReportRepository extends JpaRepository<FeverReport, Long> {
    @Query("select fr from FeverReport fr where fr.id =:id and fr.child.deletedAt is null")
    Optional<FeverReport> findById(Long id);

    @Query("SELECT fr FROM FeverReport fr JOIN FETCH fr.child c WHERE c.id =:childId AND fr.child.deletedAt is null and  fr.id <:cursor ORDER BY fr.id DESC")
    Slice<FeverReport> findFeverReportCursorPagination(Long childId, Long cursor, Pageable pageable);

    @Query("select fr from FeverReport fr join fetch fr.child c where c.id =:childId and fr.child.deletedAt is null order by fr.id desc")
    List<FeverReport> findAllByChildIdOrderByIdDesc(Long childId);
}
