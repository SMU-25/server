package final_project.momeasy.domain.fever_report.repository;

import final_project.momeasy.domain.fever_report.entity.FeverReportSymptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface FeverReportSymptomRepository extends JpaRepository<FeverReportSymptom, Long> {
    @Modifying
    @Transactional
    void deleteByFeverreportId(Long id);
}
