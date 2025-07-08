package final_project.momeasy.domain.humidity_graph.repository;

import final_project.momeasy.domain.fever_report.entity.FeverReport;
import final_project.momeasy.domain.humidity_graph.entity.HumidityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HumidityGraphRepositoy extends JpaRepository<HumidityGraph, Integer> {
    List<HumidityGraph> findByFeverReport(FeverReport feverreport);
}
