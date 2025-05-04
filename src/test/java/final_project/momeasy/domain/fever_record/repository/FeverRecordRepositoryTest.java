package final_project.momeasy.domain.fever_record.repository;

import final_project.momeasy.common.enums.Gender;
import final_project.momeasy.common.enums.Relation;
import final_project.momeasy.common.enums.Seizure;
import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.fever_record.entity.FeverRecord;
import final_project.momeasy.domain.outing_record.entity.OutingRecord;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.room_condition.entity.RoomCondition;
import final_project.momeasy.domain.room_condition.repository.RoomConditionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FeverRecordRepositoryTest {

    @Autowired
    private FeverRecordRepository feverRecordRepository;

    @Autowired
    private TestEntityManager entityManager;

    private static final int TEST_DATA_COUNT = 50;
    private Child testChild;
    private Child testChild2;
    private Parent parent;

    @BeforeEach
    void setUp() {
        // 테스트용 Child 생성
        testChild = Child.builder()
                .name("testChild")
                .birthdate(LocalDateTime.now().toLocalDate())
                .height(100)
                .weight(20.0f)
                .gender(Gender.BOY)
                .seizure(Seizure.NONE)
                .build();
        testChild = entityManager.persist(testChild);

        testChild2 = Child.builder()
                .name("testChild2")
                .birthdate(LocalDateTime.now().toLocalDate())
                .height(100)
                .weight(20.0f)
                .gender(Gender.BOY)
                .seizure(Seizure.NONE)
                .build();
        testChild2 = entityManager.persist(testChild2);

        parent = Parent.builder()
                .name("testParent")
                .loginId("loginId")
                .password("password")
                .email("test@example.com")
                .birthdate(LocalDate.of(1990, 1, 1))
                .gender(Gender.BOY)
                .build();
        parent = entityManager.persist(parent);
        parent.addChild(testChild, Relation.DAD);

        // 50개의 테스트 데이터 생성
        for (int i = 0; i < TEST_DATA_COUNT; i++) {
            FeverRecord feverRecord = FeverRecord.builder()
                    .fever(20.0f + i)
                    .child(testChild)
                    .build();
            feverRecord.setChild(testChild);
            entityManager.persist(feverRecord);
            System.out.println("created_at "+feverRecord.getCreatedAt());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void findTopByOrderByIdDesc_성공() {
        // when
        Optional<FeverRecord> topFeverRecord = feverRecordRepository.findTopByChildIdOrderByIdDesc(testChild.getId());

        // then
        assertThat(topFeverRecord).isPresent();
        assertThat(topFeverRecord.get().getFever()).isEqualTo(69.0f); // 마지막 데이터의 온도
        assertThat(topFeverRecord.get().getChild().getId()).isEqualTo(testChild.getId());

        // when
        Optional<FeverRecord> topFeverRecord2 = feverRecordRepository.findTopByChildIdOrderByIdDesc(testChild2.getId());

        // then
        assertThat(topFeverRecord2).isEmpty();
    }

    @Test
    void findAllByOrderByIdDesc_성공() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Slice<FeverRecord> feverRecords = feverRecordRepository.findAllByChildIdOrderByIdDesc(testChild.getId(),pageRequest);

        // then
        assertThat(feverRecords.getContent()).hasSize(10);
        assertThat(feverRecords.hasNext()).isTrue();

        // 첫 페이지의 첫 번째 데이터가 가장 최신 데이터인지 확인
        FeverRecord firstFeverRecord = feverRecords.getContent().get(0);
        assertThat(firstFeverRecord.getFever()).isEqualTo(69.0f);
        assertThat(firstFeverRecord.getChild().getId()).isEqualTo(testChild.getId());
    }

    @Test
    void deleteByCreatedAtBefore_성공() {
        // given
        LocalDateTime threshold = LocalDateTime.now().minusSeconds(10);
        System.out.println("threshold: " + threshold);
        // when
        feverRecordRepository.deleteByCreatedAtBefore(threshold);

        // then
        long remainingCount = feverRecordRepository.count();
        System.out.println("remainingCount: " + remainingCount);
        assertThat(remainingCount).isLessThan(TEST_DATA_COUNT);
    }
}
