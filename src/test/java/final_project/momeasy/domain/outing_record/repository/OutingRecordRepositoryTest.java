package final_project.momeasy.domain.outing_record.repository;

import final_project.momeasy.common.enums.Gender;
import final_project.momeasy.common.enums.Relation;
import final_project.momeasy.common.enums.Seizure;
import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.outing_record.entity.OutingRecord;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.room_condition.entity.RoomCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OutingRecordRepositoryTest {
    @Autowired
    private OutingRecordRepository outingRecordRepository;

    @Autowired
    private TestEntityManager entityManager;

    private static final int TEST_DATA_COUNT = 50;
    private Child testChild;
    private Child testChild2;

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

        // 50개의 테스트 데이터 생성
        for (int i = 0; i < TEST_DATA_COUNT; i++) {
            OutingRecord outingRecord = OutingRecord.builder()
                    .outing_start_time(LocalDateTime.now().minusHours(4))
                    .outing_end_time(LocalDateTime.now().minusHours(2))
                    .child(testChild)
                    .build();
            outingRecord.setChild(testChild);
            entityManager.persist(outingRecord);
            System.out.println("created_at "+outingRecord.getCreatedAt());
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
        Optional<OutingRecord> topOutingRecord = outingRecordRepository.findTopByChildIdOrderByIdDesc(testChild.getId());

        // then
        assertThat(topOutingRecord).isPresent();
        assertThat(topOutingRecord.get().getOuting_start_time()).isNotNull();
        assertThat(topOutingRecord.get().getOuting_end_time()).isNotNull();
        assertThat(topOutingRecord.get().getChild().getId()).isEqualTo(testChild.getId());

        // when
        Optional<OutingRecord> topOutingRecord2 = outingRecordRepository.findTopByChildIdOrderByIdDesc(testChild2.getId());

        // then
        assertThat(topOutingRecord2).isEmpty();
    }

    @Test
    void findAllByOrderByIdDesc_성공() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Slice<OutingRecord> outingRecords = outingRecordRepository.findAllByChildIdOrderByIdDesc(testChild.getId(),pageRequest);

        // then
        assertThat(outingRecords.getContent()).hasSize(10);
        assertThat(outingRecords.hasNext()).isTrue();

        // 첫 페이지의 첫 번째 데이터가 가장 최신 데이터인지 확인
        OutingRecord firstOutingRecord = outingRecords.getContent().get(0);
        assertThat(firstOutingRecord.getOuting_start_time()).isNotNull();
        assertThat(firstOutingRecord.getOuting_end_time()).isNotNull();
        assertThat(firstOutingRecord.getChild().getId()).isEqualTo(testChild.getId());
    }

    @Test
    void deleteByCreatedAtBefore_성공() {
        // given
        LocalDateTime threshold = LocalDateTime.now().minusSeconds(10);
        System.out.println("threshold: " + threshold);

        // when
        outingRecordRepository.deleteByCreatedAtBefore(threshold);

        // then
        long remainingCount = outingRecordRepository.count();
        System.out.println("remainingCount: " + remainingCount);
        assertThat(remainingCount).isLessThan(TEST_DATA_COUNT);
    }
}
