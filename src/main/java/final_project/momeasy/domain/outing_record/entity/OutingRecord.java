package final_project.momeasy.domain.outing_record.entity;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class OutingRecord extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime outing_start_time;

    @Column(nullable = false)
    private LocalDateTime outing_end_time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    public void setChild(Child child) {
        this.child = child;
        child.getOutingrecords().add(this);
    }

}
