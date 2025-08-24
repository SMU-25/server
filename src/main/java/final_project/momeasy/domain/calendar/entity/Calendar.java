package final_project.momeasy.domain.calendar.entity;

import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE) // 외부 생성 제한
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Calendar extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 기록 작성 날짜
    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;

    // 일정 날짜
    @Column(name = "schedule_date", nullable = false)
    private LocalDate scheduleDate;

    @Column(nullable = false)
    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    /** 양방향 편의 메서드 */
    public void setParent(Parent parent) {
        this.parent = parent;
        parent.getCalendars().add(this);
    }

    /** 수정(더티 체킹) */
    public void update(LocalDate recordDate, LocalDate scheduleDate, String title, String content) {
        this.recordDate = recordDate;
        this.scheduleDate = scheduleDate;
        this.title = title;
        this.content = content;
    }

    /** 정적 팩토리(생성 경로 통제) */
    public static Calendar create(LocalDate recordDate,
                                  LocalDate scheduleDate,
                                  String title,
                                  String content,
                                  Parent parent) {
        Calendar cal = Calendar.builder()
                .recordDate(recordDate)
                .scheduleDate(scheduleDate)
                .title(title)
                .content(content)
                .build();
        cal.setParent(parent); // 연관관계 보장
        return cal;
    }
}
