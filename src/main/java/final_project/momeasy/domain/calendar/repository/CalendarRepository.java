package final_project.momeasy.domain.calendar.repository;

import final_project.momeasy.domain.calendar.entity.Calendar;
import final_project.momeasy.domain.parent.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    // 날짜 + 부모 기준
    @Query("SELECT c FROM Calendar c WHERE c.scheduleDate = :date AND c.parent = :parent")
    List<Calendar> findByScheduleDateAndParent(@Param("date") LocalDate date,
                                               @Param("parent") Parent parent);

    // 제목에 키워드 포함 (대소문자 무시)
    List<Calendar> findByTitleContainingIgnoreCase(String keyword);

    // 부모로 전체 조회
    List<Calendar> findByParent(Parent parent);
}
