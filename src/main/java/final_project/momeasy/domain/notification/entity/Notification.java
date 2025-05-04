package final_project.momeasy.domain.notification.entity;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Notification extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    @Column(nullable = false)
    private float fever;

    @Column(nullable = false)
    private String message;

    public void setChild(Child child) {
        this.child = child;
        child.getNotifications().add(this);
    }

    public void setParent(Parent parent) {
        this.parent = parent;
        parent.getNotifications().add(this);
    }

}
