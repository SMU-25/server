package final_project.momeasy.domain.symptom.entity;

import final_project.momeasy.common.enums.SymptomType;
import final_project.momeasy.domain.fever_report.entity.FeverReport;
import final_project.momeasy.domain.fever_report.entity.FeverReportSymptom;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Symptom {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SymptomType symptom;

    @Column(nullable = false)
    private String symptom_name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "symptom", cascade = CascadeType.ALL)
    @Builder.Default
    private List<FeverReportSymptom> feverReportSymptoms = new ArrayList<>();

    public void addFeverReport(FeverReport feverReport) {
        FeverReportSymptom feverReportSymptom =
                FeverReportSymptom.builder().
                        feverreport(feverReport).
                        symptom(this).build();
        this.feverReportSymptoms.add(feverReportSymptom);
        feverReport.getFeverReportSymptoms().add(feverReportSymptom);
    }
}
