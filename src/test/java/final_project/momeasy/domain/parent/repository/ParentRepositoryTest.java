package final_project.momeasy.domain.parent.repository;

import final_project.momeasy.common.enums.Gender;
import final_project.momeasy.common.enums.Relation;
import final_project.momeasy.common.enums.Seizure;
import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.parent.entity.ParentChild;
import final_project.momeasy.domain.setting.entity.Setting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ParentRepositoryTest {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Parent parent;
    private Child child;
    private Child child2;
    private Setting setting;

    @BeforeEach
    void setUp() {
        // Parent 생성
        parent = Parent.builder()
                .name("testParent")
                .loginId("loginId")
                .password("password")
                .email("test@example.com")
                .gender(Gender.BOY)
                .birthdate(LocalDate.of(1990, 1, 1))
                .build();
        parent = entityManager.persist(parent);

        // Child 생성
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
                .name("kim")
                .birthdate(LocalDate.now())
                .height(163)
                .weight(59.7f)
                .gender(Gender.BOY)
                .seizure(Seizure.NONE)
                .build();
        child2 = entityManager.persist(child2);

        
        // Parent와 Child 연결
        parent.addChild(child, Relation.DAD);
        parent.addChild(child2, Relation.DAD);

        // Parent와 Setting 연결
        
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void findById_성공() {
        // when
        Optional<Parent> foundParent = parentRepository.findById(parent.getId());

        // then
        assertThat(foundParent).isPresent();
        assertThat(foundParent.get().getName()).isEqualTo("testParent");
    }

    @Test
    void findByEmail_성공() {
        // when
        Optional<Parent> foundParent = parentRepository.findByEmail("test@example.com");

        // then
        assertThat(foundParent).isPresent();
        assertThat(foundParent.get().getEmail()).isEqualTo("test@example.com");
    }

} 