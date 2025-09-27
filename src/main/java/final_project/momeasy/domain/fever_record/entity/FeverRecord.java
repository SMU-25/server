package final_project.momeasy.domain.fever_record.entity;

import final_project.momeasy.common.enums.RecordState;
import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class FeverRecord extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private float fever;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecordState recordState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id",nullable = false)
    private Child child;

    public void setChild(Child child) {
        this.child = child;
        child.getFeverRecords().add(this);
    }
}
