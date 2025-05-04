package final_project.momeasy.domain.illness.repository;

import final_project.momeasy.common.enums.Gender;
import final_project.momeasy.common.enums.IllnessType;
import final_project.momeasy.common.enums.Seizure;
import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.child.entity.ChildIllness;
import final_project.momeasy.domain.illness.entity.Illness;
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
class IllnessRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IllnessRepository illnessRepository;

    private Child child1;
    private Child child2;
    private Illness illness1;
    private Illness illness2;
    private Illness illness3;

    @BeforeEach
    void setup() {
        // 자식 생성
        child1 = Child.builder()
                .name("child1")
                .birthdate(LocalDate.of(2020, 1, 1))
                .height(100)
                .weight(15.5f)
                .gender(Gender.BOY)
                .seizure(Seizure.NONE)
                .build();

        child2 = Child.builder()
                .name("child2")
                .birthdate(LocalDate.of(2021, 2, 2))
                .height(90)
                .weight(13.5f)
                .gender(Gender.GIRL)
                .seizure(Seizure.NONE)
                .build();

        entityManager.persist(child1);
        entityManager.persist(child2);

        // 질병 생성
        illness1 = Illness.builder()
                .illness(IllnessType.아토피)
                .type_name("아토피 피부염")
                .build();
        illness1.addChild(child1);

        illness2 = Illness.builder()
                .illness(IllnessType.천식)
                .type_name("알레르기성 천식")
                .build();
        illness2.addChild(child1);

        illness3 = Illness.builder()
                .illness(IllnessType.폐_질환)
                .type_name("만성 폐쇄성 폐질환")
                .build();

        illness3.addChild(child2);
        entityManager.persist(illness1);
        entityManager.persist(illness2);
        entityManager.persist(illness3);
        // 자식-질병 관계 설정

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void findByChildId_shouldReturnAllIllnessesForChild() {
        // when
        List<Illness> illnesses = illnessRepository.findByChildId(child1.getId());

        // then
        assertThat(illnesses).hasSize(2);
        assertThat(illnesses).extracting(Illness::getIllness)
                .containsExactlyInAnyOrder(IllnessType.아토피, IllnessType.천식);
    }

    @Test
    void findByChildId_shouldReturnEmptyWhenChildHasNoIllnesses() {
        // given
        Child childWithoutIllness = Child.builder()
                .name("child3")
                .birthdate(LocalDate.of(2022, 3, 3))
                .height(80)
                .weight(12.0f)
                .gender(Gender.BOY)
                .seizure(Seizure.NONE)
                .build();
        entityManager.persist(childWithoutIllness);
        entityManager.flush();
        entityManager.clear();

        // when
        List<Illness> illnesses = illnessRepository.findByChildId(childWithoutIllness.getId());

        // then
        assertThat(illnesses).isEmpty();
    }
} 