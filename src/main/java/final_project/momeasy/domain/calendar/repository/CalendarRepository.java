package final_project.momeasy.domain.calendar.repository;

import final_project.momeasy.domain.calendar.entity.Calendar;
import final_project.momeasy.domain.parent.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    List<Calendar> findByRecordDateAndParent(LocalDate recordDate, Parent parent);
    List<Calendar> findByTitleContainingIgnoreCase(String keyword);
    List<Calendar> findByParent(Parent parent);
}
