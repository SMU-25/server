package final_project.momeasy.domain.calendar.repository;

import final_project.momeasy.domain.calendar.entity.Calendar;
import final_project.momeasy.domain.parent.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    List<Calendar> findByRecordDateAndParent(LocalDate recordDate, Parent parent);

    List<Calendar> findByScheduleDateAndParent(LocalDate scheduleDate, Parent parent);

    List<Calendar> findByTitleContainingIgnoreCaseAndParent(String keyword, Parent parent);

    List<Calendar> findByParent(Parent parent);

    Optional<Calendar> findByIdAndParent(Long id, Parent parent); // 소유자 검증용
}
