package final_project.momeasy.domain.temperature_graph.repository;

import final_project.momeasy.domain.fever_report.entity.FeverReport;
import final_project.momeasy.domain.temperature_graph.entity.TemperatureGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemperatureGraphRepository extends JpaRepository<TemperatureGraph, Long> {
    List<TemperatureGraph> findByFeverReport(FeverReport feverreport);
}
