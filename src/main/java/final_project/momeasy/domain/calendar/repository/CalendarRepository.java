package final_project.momeasy.domain.calendar.repository;

import final_project.momeasy.domain.calendar.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    @Query("SELECT c FROM Calendar c WHERE YEAR(c.recordDate) = :year AND MONTH(c.recordDate) = :month AND DAY(c.recordDate) = :day")
    List<Calendar> findByYearMonthDay(@Param("year") int year, @Param("month") int month, @Param("day") int day);

}
