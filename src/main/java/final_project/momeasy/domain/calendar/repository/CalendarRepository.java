package final_project.momeasy.domain.calendar.repository;

import final_project.momeasy.domain.calendar.entity.Calendar;
import final_project.momeasy.domain.parent.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    // 날짜만 기준
    @Query("SELECT c FROM Calendar c WHERE YEAR(c.scheduleDate) = :year AND MONTH(c.scheduleDate) = :month AND DAY(c.scheduleDate) = :day")
    List<Calendar> findByYearMonthDay(@Param("year") int year,
                                      @Param("month") int month,
                                      @Param("day") int day);

    // 날짜 + 부모 기준
    @Query("SELECT c FROM Calendar c WHERE YEAR(c.scheduleDate) = :year AND MONTH(c.scheduleDate) = :month AND DAY(c.scheduleDate) = :day AND c.parent = :parent")
    List<Calendar> findByYearMonthDayAndParent(@Param("year") int year,
                                               @Param("month") int month,
                                               @Param("day") int day,
                                               @Param("parent") Parent parent);

    // 제목에 키워드 포함 (대소문자 무시)
    List<Calendar> findByTitleContainingIgnoreCase(String keyword);

    // 부모로 전체 조회
    List<Calendar> findByParent(Parent parent);
}
