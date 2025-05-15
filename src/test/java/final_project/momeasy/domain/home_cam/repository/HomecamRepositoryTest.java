package final_project.momeasy.domain.home_cam.repository;

import final_project.momeasy.common.enums.Gender;
import final_project.momeasy.common.enums.Seizure;
import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.child.repository.ChildRepository;
import final_project.momeasy.domain.home_cam.entity.Homecam;
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
class HomecamRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HomecamRepository homecamRepository;

    @Autowired
    private ChildRepository childRepository;

    private Parent parent;
    private Child child;
    private Child child2;
    private Child child3;
    private Homecam homecam1;
    private Homecam homecam2;
    private Homecam homecam3;

    @BeforeEach
    void setup() {
        // 부모 생성
        parent = Parent.builder()
                .name("testParent")
                .loginId("testLoginId")
                .password("testPassword")
                .email("test@example.com")
                .birthdate(LocalDate.of(1980, 1, 1))
                .gender(Gender.GIRL)
                .build();
        entityManager.persist(parent);

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
                .name("testChild2")
                .birthdate(LocalDate.now())
                .height(101)
                .weight(22.0f)
                .gender(Gender.BOY)
                .seizure(Seizure.NONE)
                .build();
        child2 = entityManager.persist(child2);

        child3 = Child.builder()
                .name("testChild3")
                .birthdate(LocalDate.now())
                .height(102)
                .weight(23.0f)
                .gender(Gender.BOY)
                .seizure(Seizure.NONE)
                .build();
        child3 = entityManager.persist(child3);
        // 홈캠 생성
        homecam1 = Homecam.builder()
                .name("Living Room Camera")
                .serial_num("SN12345")
                .place("Living Room")
                .date("2023-01-01")
                .video_url("http://example.com/video1")
                .build();
        homecam1.setParent(parent);
        homecam1.setChild(child);

        homecam2 = Homecam.builder()
                .name("Bedroom Camera")
                .serial_num("SN67890")
                .place("Bedroom")
                .date("2023-01-02")
                .video_url("http://example.com/video2")
                .build();
        homecam2.setParent(parent);
        homecam2.setChild(child2);

        homecam3 = Homecam.builder()
                .name("Kitchen Camera")
                .serial_num("SN24680")
                .place("Kitchen")
                .date("2023-01-03")
                .video_url("http://example.com/video3")
                .build();
        homecam3.setParent(parent);
        homecam3.setChild(child3);

        entityManager.persist(homecam1);
        entityManager.persist(homecam2);
        entityManager.persist(homecam3);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void findAllByParentId_shouldReturnAllHomecamsForParent() {
        // when
        List<Homecam> homecams = homecamRepository.findAllByParentId(parent.getId());

        // then
        assertThat(homecams).hasSize(3);
        assertThat(homecams).extracting("name")
                .containsExactlyInAnyOrder("Living Room Camera", "Bedroom Camera", "Kitchen Camera");
    }

    @Test
    void findAllByParentId_shouldReturnEmptyListWhenNoHomecamsExist() {
        // given
        Long nonExistentParentId = 999L;

        // when
        List<Homecam> homecams = homecamRepository.findAllByParentId(nonExistentParentId);

        // then
        assertThat(homecams).isEmpty();
    }

    @Test
    void findById_shouldReturnHomecamWhenExists() {
        // when
        Optional<Homecam> foundHomecam = homecamRepository.findById(homecam1.getId());

        // then
        assertThat(foundHomecam).isPresent();
        assertThat(foundHomecam.get().getId()).isEqualTo(homecam1.getId());
        assertThat(foundHomecam.get().getName()).isEqualTo("Living Room Camera");
        assertThat(foundHomecam.get().getSerial_num()).isEqualTo("SN12345");
        assertThat(foundHomecam.get().getPlace()).isEqualTo("Living Room");
    }

    @Test
    void findById_shouldReturnEmptyWhenNotExists() {
        // given
        Long nonExistentId = 999L;

        // when
        Optional<Homecam> foundHomecam = homecamRepository.findById(nonExistentId);

        // then
        assertThat(foundHomecam).isEmpty();
    }

    @Test
    void checkChildhasHomecam(){
        // when
        Optional<Child> foundChild1 = childRepository.findById(child.getId());
        Optional<Child> foundChild2 = childRepository.findById(child2.getId());
        Optional<Child> foundChild3 = childRepository.findById(child3.getId());

        // then
        assertThat(foundChild1.get().getHomecam().getName()).isEqualTo("Living Room Camera");
        assertThat(foundChild1.get().getHomecam().getSerial_num()).isEqualTo("SN12345");
        assertThat(foundChild1.get().getHomecam().getPlace()).isEqualTo("Living Room");

        assertThat(foundChild2.get().getHomecam().getName()).isEqualTo("Bedroom Camera");
        assertThat(foundChild2.get().getHomecam().getSerial_num()).isEqualTo("SN67890");
        assertThat(foundChild2.get().getHomecam().getPlace()).isEqualTo("Bedroom");

        assertThat(foundChild3.get().getHomecam().getName()).isEqualTo("Kitchen Camera");
        assertThat(foundChild3.get().getHomecam().getSerial_num()).isEqualTo("SN24680");
        assertThat(foundChild3.get().getHomecam().getPlace()).isEqualTo("Kitchen");
    }
}
