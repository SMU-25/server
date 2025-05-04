package final_project.momeasy.domain.room_condition.entity;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class RoomCondition extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private float temperature;

    @Column(nullable = false)
    private float humidity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    public void setChild(Child child) {
        this.child = child;
        child.getRoomConditions().add(this);
    }

}
