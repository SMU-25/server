package final_project.momeasy.domain.calendar.entity;

import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE) // 외부에서 무분별한 생성 방지
public class Calendar extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate recordDate;

    @Column(nullable = false)
    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    public void setParent(Parent parent) {
        this.parent = parent;
        parent.getCalendars().add(this);
    }

    public void update(LocalDate recordDate, String title, String content) {
        this.recordDate = recordDate;
        this.title = title;
        this.content = content;
    }
}
