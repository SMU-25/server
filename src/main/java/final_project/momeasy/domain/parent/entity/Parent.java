package final_project.momeasy.domain.parent.entity;

import final_project.momeasy.common.enums.Gender;
import final_project.momeasy.common.enums.Relation;
import final_project.momeasy.domain.calendar.entity.Calendar;
import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.home_cam.entity.Homecam;
import final_project.momeasy.domain.notification.entity.Notification;
import final_project.momeasy.domain.setting.entity.Setting;
import final_project.momeasy.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Parent extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "login_id", nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate birthdate;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ParentChild> parentchild = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Homecam> homecams = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Calendar> calendars = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "parent")
    private Setting setting;

    // 연관 관계 메서드
    public void addChild(Child child, Relation relation) {
        ParentChild parentChild = ParentChild.builder()
                .parent(this)
                .child(child)
                .relation(relation)
                .build();

        this.parentchild.add(parentChild);
        child.getParentChildren().add(parentChild);
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
        setting.setParent(this);
    }

}
