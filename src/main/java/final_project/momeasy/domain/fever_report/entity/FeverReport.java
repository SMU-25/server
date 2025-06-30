package final_project.momeasy.domain.fever_report.entity;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.fever_graph.entity.FeverGraph;
import final_project.momeasy.domain.fever_report.dto.FeverReportRequestDTO;
import final_project.momeasy.domain.humidity_graph.entity.HumidityGraph;
import final_project.momeasy.domain.temperature_graph.entity.TemperatureGraph;
import final_project.momeasy.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class FeverReport extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String etc_symptom;

    private String special;

    @Column(nullable = false)
    private String outing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    // 이름 카멜 케이스로 변경하기
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "feverreport", cascade = CascadeType.ALL)
    @Builder.Default
    private List<FeverReportSymptom>  feverReportSymptoms = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "feverReport", cascade = CascadeType.ALL)
    @Builder.Default
    private List<FeverGraph> feverGraphs = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "feverReport", cascade = CascadeType.ALL)
    @Builder.Default
    private List<HumidityGraph> humidityGraphs = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "feverReport", cascade = CascadeType.ALL)
    @Builder.Default
    private List<TemperatureGraph> temperatureGraphs = new ArrayList<>();

    public void setChild(Child child) {
        this.child = child;
        child.getFever_reports().add(this);
    }

    public void updateFeverReport(FeverReportRequestDTO.FeverReportUpdateDTO feverReportUpdateDTO,String special) {
        this.etc_symptom = feverReportUpdateDTO.getEtc_symptom();
        this.outing = feverReportUpdateDTO.getOuting();
        this.special = special;
    }
}
