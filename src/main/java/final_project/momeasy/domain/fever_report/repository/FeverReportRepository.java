package final_project.momeasy.domain.fever_report.repository;

import final_project.momeasy.domain.fever_report.entity.FeverReport;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeverReportRepository extends JpaRepository<FeverReport, Long> {
    Optional<FeverReport> findById(Long id);
    Slice<FeverReport> findAllByChildIdOrderByIdDesc(Long ChildId, Pageable pageable);
}
