package final_project.momeasy.domain.fever_report.repository;

import final_project.momeasy.common.enums.Gender;
import final_project.momeasy.common.enums.Seizure;
import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.fever_report.entity.FeverReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FeverReportRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FeverReportRepository feverReportRepository;

    private Child child;
    private FeverReport feverReport1;
    private FeverReport feverReport2;
    private FeverReport feverReport3;

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
        feverReport1 = FeverReport.builder()
                .etc_symptom("기침")
                .special("특이사항 없음")
                .outing("외출 없음")
                .build();
        feverReport1.setChild(child);

        feverReport2 = FeverReport.builder()
                .etc_symptom("콧물")
                .special("특이사항 없음")
                .outing("외출 없음")
                .build();
        feverReport2.setChild(child);

        feverReport3 = FeverReport.builder()
                .etc_symptom("기침, 콧물")
                .special("특이사항 없음")
                .outing("외출 없음")
                .build();
        feverReport3.setChild(child);

        entityManager.persist(feverReport1);
        entityManager.persist(feverReport2);
        entityManager.persist(feverReport3);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void findById_shouldReturnFeverReportWhenExists() {
        // when
        Optional<FeverReport> foundReport = feverReportRepository.findById(feverReport1.getId());

        // then
        assertThat(foundReport).isPresent();
        assertThat(foundReport.get().getId()).isEqualTo(feverReport1.getId());
        assertThat(foundReport.get().getEtc_symptom()).isEqualTo("기침");
    }

    @Test
    void findById_shouldReturnEmptyWhenNotExists() {
        // given
        Long nonExistentId = 999L;

        // when
        Optional<FeverReport> foundReport = feverReportRepository.findById(nonExistentId);

        // then
        assertThat(foundReport).isEmpty();
    }

    @Test
    void findAllByOrderByIdDesc_shouldReturnFeverReportsInDescendingOrder() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 2); // 첫 페이지, 페이지당 2개 항목

        // when
        Slice<FeverReport> reports = feverReportRepository.findAllByOrderByIdDesc(pageRequest);

        // then
        assertThat(reports.getContent()).hasSize(2);
        assertThat(reports.getContent().get(0).getId())
                .isGreaterThan(reports.getContent().get(1).getId());
    }
} 