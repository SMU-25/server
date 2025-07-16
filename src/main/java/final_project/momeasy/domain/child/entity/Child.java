package final_project.momeasy.domain.child.entity;

import final_project.momeasy.common.enums.Gender;
import final_project.momeasy.domain.child.dto.request.ChildRequestDTO;
import final_project.momeasy.domain.fever_record.entity.FeverRecord;
import final_project.momeasy.domain.fever_report.entity.FeverReport;
import final_project.momeasy.domain.home_cam.entity.Homecam;
import final_project.momeasy.domain.illness.entity.Illness;
import final_project.momeasy.domain.notification.entity.Notification;
import final_project.momeasy.domain.parent.entity.ParentChild;
import final_project.momeasy.common.enums.Seizure;
import final_project.momeasy.domain.room_condition.entity.RoomCondition;
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
public class Child extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false)
    private Float height;

    @Column(nullable = false)
    private Float weight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Seizure seizure;

    private String profileImage;

    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "child",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @Builder.Default
    private List<ParentChild> parentChildren = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "child", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "child", cascade = CascadeType.ALL)
    @Builder.Default
    private List<RoomCondition> roomConditions = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "child", cascade = CascadeType.ALL)
    @Builder.Default
    private List<FeverRecord> feverRecords = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "child", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ChildIllness> childIllnesses = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "child", cascade = CascadeType.ALL)
    @Builder.Default
    private List<FeverReport> fever_reports = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "child", cascade = CascadeType.ALL)
    private Homecam homecam;

    public void setHomecam(Homecam homecam) {
        this.homecam = homecam;
    }

    public void addIllness(Illness illness) {
        ChildIllness childIllness = ChildIllness.builder()
                .child(this)
                .illness(illness).build();

        this.childIllnesses.add(childIllness);
        illness.getChildIllnesses().add(childIllness);
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void update(ChildRequestDTO.ChildUpdateRequestDTO dto) {
        this.name = dto.name();
        this.birthdate = dto.birthdate();
        this.height = dto.height();
        this.weight = dto.weight();
        this.gender = dto.gender();
        this.seizure = dto.seizure();
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}
