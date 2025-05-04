package final_project.momeasy.domain.symptom.repository;

import final_project.momeasy.common.enums.Gender;
import final_project.momeasy.common.enums.Seizure;
import final_project.momeasy.common.enums.SymptomType;
import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.fever_report.entity.FeverReport;
import final_project.momeasy.domain.symptom.entity.Symptom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SymptomRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SymptomRepository symptomRepository;

    private Child child;
    private FeverReport feverReport;
    private Symptom symptom1;
    private Symptom symptom2;
    private Symptom symptom3;

    @BeforeEach
    void setup() {
        // 자식 생성
        child = Child.builder()
                .name("testChild")
                .birthdate(LocalDate.of(2020, 1, 1))
                .height(100)
                .weight(15.5f)
                .gender(Gender.BOY)
                .seizure(Seizure.NONE)
                .build();
        entityManager.persist(child);

        // 발열 보고서 생성
        feverReport = FeverReport.builder()
                .etc_symptom("기침")
                .special("특이사항 없음")
                .outing("외출 없음")
                .build();
        feverReport.setChild(child);
        entityManager.persist(feverReport);

        // 증상 생성
        symptom1 = Symptom.builder()
                .symptom(SymptomType.발열)
                .symptom_name("고열")
                .build();

        symptom2 = Symptom.builder()
                .symptom(SymptomType.기침)
                .symptom_name("마른기침")
                .build();

        symptom3 = Symptom.builder()
                .symptom(SymptomType.콧물)
                .symptom_name("맑은 콧물")
                .build();

        // 증상과 발열 보고서 연결
        symptom1.addFeverReport(feverReport);
        symptom2.addFeverReport(feverReport);

        entityManager.persist(symptom1);
        entityManager.persist(symptom2);
        entityManager.persist(symptom3);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void findByFeverReportId_shouldReturnAllSymptomsForFeverReport() {
        // when
        List<Symptom> symptoms = symptomRepository.findByFeverReportId(feverReport.getId());

        // then
        assertThat(symptoms).hasSize(2);
        assertThat(symptoms).extracting(Symptom::getSymptom)
                .containsExactlyInAnyOrder(SymptomType.발열, SymptomType.기침);
    }

    @Test
    void findByFeverReportId_shouldReturnEmptyWhenNoSymptoms() {
        // given
        FeverReport newFeverReport = FeverReport.builder()
                .etc_symptom("없음")
                .special("특이사항 없음")
                .outing("외출 없음")
                .build();
        newFeverReport.setChild(child);
        entityManager.persist(newFeverReport);
        entityManager.flush();
        entityManager.clear();

        // when
        List<Symptom> symptoms = symptomRepository.findByFeverReportId(newFeverReport.getId());

        // then
        assertThat(symptoms).isEmpty();
    }
} 