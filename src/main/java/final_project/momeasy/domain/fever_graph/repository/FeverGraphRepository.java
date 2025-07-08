package final_project.momeasy.domain.fever_graph.repository;

import final_project.momeasy.domain.fever_graph.entity.FeverGraph;
import final_project.momeasy.domain.fever_report.entity.FeverReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeverGraphRepository extends JpaRepository<FeverGraph, Long> {
    List<FeverGraph> findByFeverReport(FeverReport feverreport);
}
