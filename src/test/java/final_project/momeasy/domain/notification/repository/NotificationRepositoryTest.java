package final_project.momeasy.domain.notification.repository;

import final_project.momeasy.common.enums.Gender;
import final_project.momeasy.common.enums.Seizure;
import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.notification.entity.Notification;
import final_project.momeasy.domain.parent.entity.Parent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NotificationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private NotificationRepository notificationRepository;

    private Parent parent1;
    private Parent parent2;
    private Child child1;
    private Child child2;
    private Child child3;

    @BeforeEach
    void setup() {
        // 부모 생성
        parent1 = Parent.builder()
                .name("parent1")
                .password("password1")
                .email("parent1@test.com")
                .createdAt(LocalDateTime.now())
                .gender(Gender.BOY)
                .build();

        parent2 = Parent.builder()
                .name("parent2")
                .password("password2")
                .email("parent2@test.com")
                .createdAt(LocalDateTime.now())
                .gender(Gender.GIRL)
                .build();

        entityManager.persist(parent1);
        entityManager.persist(parent2);

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

        child3 = Child.builder()
                .name("child3")
                .birthdate(LocalDate.of(2022, 3, 3))
                .height(80)
                .weight(12.0f)
                .gender(Gender.BOY)
                .seizure(Seizure.NONE)
                .build();

        entityManager.persist(child1);
        entityManager.persist(child2);
        entityManager.persist(child3);
    }

    @Test
    void findByParentId_shouldReturnAllNotificationsForParent() {
        // given
        for(int i=0; i<15; i++){
            Notification notification = Notification.builder()
                    .parent(parent1)
                    .child(child1)
                    .fever(37.0f)
                    .message("메시지입니다."+i)
                    .build();
            notification.setParent(parent1);
            notification.setChild(child1);
            entityManager.persist(notification);
        }

        entityManager.flush();
        entityManager.clear();

        // when
        PageRequest pageRequest = PageRequest.of(0, 10);
        Slice<Notification> notifications = notificationRepository.findByParentId(parent1.getId(),pageRequest);
        PageRequest pageRequest1 = PageRequest.of(1, 10);
        Slice<Notification> notifications2 = notificationRepository.findByParentId(parent1.getId(),pageRequest1);
        // then
        assertThat(notifications).hasSize(10);
        assertThat(notifications2).hasSize(5);
    }

    @Test
    void findTopByParentIdAndChildIdOrderByIdDesc_shouldReturnLatestNotification() {
        // given
        Notification oldNotification = Notification.builder()
                .parent(parent1)
                .child(child1)
                .build();
        
        Notification latestNotification = Notification.builder()
                .parent(parent1)
                .child(child1)
                .build();

        oldNotification.setParent(parent1);
        oldNotification.setChild(child1);
        latestNotification.setParent(parent1);
        latestNotification.setChild(child1);

        entityManager.persist(oldNotification);
        entityManager.persist(latestNotification);
        entityManager.flush();
        entityManager.clear();

        // when
        Notification foundNotification = notificationRepository
                .findTopByParentIdAndChildIdOrderByIdDesc(parent1.getId(), child1.getId())
                .orElse(null);

        // then
        assertThat(foundNotification).isNotNull();
        assertThat(foundNotification.getId()).isEqualTo(latestNotification.getId());
    }

    @Test
    void findTopByParentIdAndChildIdOrderByIdDesc_shouldReturnEmptyWhenNoNotificationExists() {
        // given
        long nonExistentParentId = 999L;
        long nonExistentChildId = 999L;

        // when
        var result = notificationRepository.findTopByParentIdAndChildIdOrderByIdDesc(nonExistentParentId, nonExistentChildId);

        // then
        assertThat(result).isEmpty();
    }
} 