package final_project.momeasy.domain.symptom.repository;

import final_project.momeasy.common.enums.SymptomType;
import final_project.momeasy.domain.symptom.entity.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {
    @Query("select s from Symptom s join fetch FeverReportSymptom fs on s.id = fs.symptom.id where fs.feverreport.id =:feverreportId")
    List<Symptom> findByFeverReportId(@Param("feverreportId") Long FeverReportId);

    Optional<Symptom> findBySymptom(SymptomType symptomType);
}
