package final_project.momeasy.domain.calendar.repository;

import final_project.momeasy.common.enums.Gender;
import final_project.momeasy.domain.calendar.entity.Calendar;
import final_project.momeasy.domain.parent.entity.Parent;
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
class CalendarRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CalendarRepository calendarRepository;

    private Parent parent;
    private Calendar calendar1;
    private Calendar calendar2;
    private Calendar calendar3;
    private Calendar calendar4;
    private Calendar calendar5;
    private Calendar calendar6;

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

        // 2025년 4월 9일에 기록된 캘린더 2개 생성
        calendar1 = Calendar.builder()
                .recordDate(LocalDate.of(2025, 4, 9))
                .title("일정 1")
                .content("2025년 4월 9일 일정 내용 1")
                .build();
        calendar1.setParent(parent);

        calendar2 = Calendar.builder()
                .recordDate(LocalDate.of(2025, 4, 9))
                .title("일정 2")
                .content("2025년 4월 9일 일정 내용 2")
                .build();
        calendar2.setParent(parent);

        // 2025년 4월 15일에 기록된 캘린더 3개 생성
        calendar3 = Calendar.builder()
                .recordDate(LocalDate.of(2025, 4, 15))
                .title("일정 3")
                .content("2025년 4월 15일 일정 내용 1")
                .build();
        calendar3.setParent(parent);

        calendar4 = Calendar.builder()
                .recordDate(LocalDate.of(2025, 4, 15))
                .title("일정 4")
                .content("2025년 4월 15일 일정 내용 2")
                .build();
        calendar4.setParent(parent);

        calendar5 = Calendar.builder()
                .recordDate(LocalDate.of(2025, 4, 15))
                .title("일정 5")
                .content("2025년 4월 15일 일정 내용 3")
                .build();
        calendar5.setParent(parent);

        // 2025년 4월 19일에 기록된 캘린더 1개 생성
        calendar6 = Calendar.builder()
                .recordDate(LocalDate.of(2025, 4, 19))
                .title("일정 6")
                .content("2025년 4월 19일 일정 내용 1")
                .build();
        calendar6.setParent(parent);

        entityManager.persist(calendar1);
        entityManager.persist(calendar2);
        entityManager.persist(calendar3);
        entityManager.persist(calendar4);
        entityManager.persist(calendar5);
        entityManager.persist(calendar6);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void findByYearMonthDay_shouldReturnCalendarsForSpecificDate() {
        // 2025년 4월 9일 데이터 조회 (2개)
        List<Calendar> calendarsForApril9 = calendarRepository.findByYearMonthDay(2025, 4, 9);
        assertThat(calendarsForApril9).hasSize(2);
        assertThat(calendarsForApril9).extracting("title").containsExactlyInAnyOrder("일정 1", "일정 2");

        // 2025년 4월 15일 데이터 조회 (3개)
        List<Calendar> calendarsForApril15 = calendarRepository.findByYearMonthDay(2025, 4, 15);
        assertThat(calendarsForApril15).hasSize(3);
        assertThat(calendarsForApril15).extracting("title").containsExactlyInAnyOrder("일정 3", "일정 4", "일정 5");

        // 2025년 4월 19일 데이터 조회 (1개)
        List<Calendar> calendarsForApril19 = calendarRepository.findByYearMonthDay(2025, 4, 19);
        assertThat(calendarsForApril19).hasSize(1);
        assertThat(calendarsForApril19).extracting("title").containsExactly("일정 6");

        // 존재하지 않는 날짜 조회 (0개)
        List<Calendar> calendarsForApril20 = calendarRepository.findByYearMonthDay(2025, 4, 20);
        assertThat(calendarsForApril20).isEmpty();
    }
}