package final_project.momeasy.domain.child.repository;

import final_project.momeasy.common.enums.Gender;
import final_project.momeasy.common.enums.Relation;
import final_project.momeasy.common.enums.Seizure;
import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.parent.entity.Parent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChildRepositoryTest {

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Parent parent;
    private Child child;
    private Child child2;

    @BeforeEach
    void setUp() {
        // Child 생성
        parent = Parent.builder()
                .name("testParent")
                .loginId("loginId")
                .password("password")
                .email("test@example.com")
                .gender(Gender.BOY)
                .birthdate(LocalDate.of(1990, 1, 1))
                .build();
        parent = entityManager.persist(parent);


        child = Child.builder()
                .name("testChild")
                .birthdate(LocalDate.now())
                .height(100)
                .weight(20.0f)
                .gender(Gender.BOY)
                .seizure(Seizure.NONE)
                .build();
        child = entityManager.persist(child);

        child2 = Child.builder()
                .name("김준형")
                .birthdate(LocalDate.now())
                .height(163)
                .weight(59.7f)
                .gender(Gender.BOY)
                .seizure(Seizure.YES)
                .build();
        child2 = entityManager.persist(child2);

        parent.addChild(child, Relation.DAD);
        parent.addChild(child2, Relation.DAD);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void findById_성공() {
        // when
        Optional<Child> foundChild = childRepository.findById(child.getId());
        Optional<Child> foundChild2 = childRepository.findById(child2.getId());

        // then
        assertThat(foundChild).isPresent();
        assertThat(foundChild.get().getName()).isEqualTo("testChild");
        assertThat(foundChild.get().getHeight()).isEqualTo(100);
        assertThat(foundChild.get().getWeight()).isEqualTo(20.0f);
        assertThat(foundChild2.get().getHeight()).isEqualTo(163);
        assertThat(foundChild2.get().getWeight()).isEqualTo(59.7f);
    }

    @Test
    void findChildByParentId_성공(){
        //when
        Optional<Child> foundChild = childRepository.findById(child.getId());

        //then
        assertThat(foundChild).isPresent();
        assertThat(foundChild.get().getName()).isEqualTo("testChild");
        assertThat(foundChild.get().getHeight()).isEqualTo(100);
        assertThat(foundChild.get().getWeight()).isEqualTo(20.0f);
    }

    @Test
    void findByParentId_성공() {
        // when
        List<Child> children = childRepository.findByParentId(parent.getId());

        // then
        assertThat(children).hasSize(2);
        assertThat(children).extracting("name").containsExactlyInAnyOrder("testChild", "김준형");
        assertThat(children).extracting("height").containsExactlyInAnyOrder(100, 163);
        assertThat(children).extracting("weight").containsExactlyInAnyOrder(20.0f, 59.7f);
    }
} 