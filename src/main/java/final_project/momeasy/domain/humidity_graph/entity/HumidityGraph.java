package final_project.momeasy.domain.humidity_graph.entity;

import final_project.momeasy.common.enums.DayRange;
import final_project.momeasy.domain.fever_report.entity.FeverReport;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HumidityGraph {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private float humidity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayRange dayRange;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fever_report_id", nullable = false)
    private FeverReport feverReport;

    public void setFeverReport(FeverReport feverReport) {
        this.feverReport = feverReport;
        feverReport.getHumidityGraphs().add(this);
    }
}
