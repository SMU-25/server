package final_project.momeasy.domain.notification.entity;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.entity.BaseEntity;
import final_project.momeasy.common.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type; // CARE 로 고정

    @Column
    private Float fever;           // 체온

    @Column
    private Float temperature;     // 방 온도

    @Column
    private Float humidity;        // 습도

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private boolean isRead;

    // 연관관계 편의 메서드
    public void setChild(Child child) {
        this.child = child;
        child.getNotifications().add(this);
    }

    public void setParent(Parent parent) {
        this.parent = parent;
        parent.getNotifications().add(this);
    }

    public void markAsRead() {
        this.isRead = true;
    }
}
