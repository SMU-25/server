package final_project.momeasy.domain.parent.entity;

import final_project.momeasy.common.enums.Gender;
import final_project.momeasy.common.enums.Role;
import final_project.momeasy.common.enums.SocialType;
import final_project.momeasy.domain.calendar.entity.Calendar;
import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.home_cam.entity.Homecam;
import final_project.momeasy.domain.notification.entity.Notification;
import final_project.momeasy.domain.setting.entity.Setting;
import final_project.momeasy.global.entity.BaseEntity;
import final_project.momeasy.global.fcm.entity.FcmToken;
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

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate birthdate;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private LocalDateTime deletedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ParentChild> parentChild = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Homecam> homecams = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Calendar> calendars = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    @Builder.Default
    private List<FcmToken> fcmTokens = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    private Setting setting;

    // 연관 관계 메서드
    public void addChild(Child child) {
        ParentChild parentChild = ParentChild.builder()
                .parent(this)
                .child(child)
                .build();

        this.parentChild.add(parentChild);
        child.getParentChildren().add(parentChild);
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
        setting.setParent(this);
    }

    public void addFcmToken(FcmToken token) {
        this.fcmTokens.add(token);
        token.setParent(this);
    }

    // soft delete
    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }

    // check delete
    public boolean isDeleted() {
        return deletedAt != null;
    }

    // undo delete
    public void undoDelete() {
        this.deletedAt = null;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateInfo(String name, LocalDate birthdate, Gender gender) {
        this.name = name;
        this.birthdate = birthdate;
        this.gender = gender;
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}