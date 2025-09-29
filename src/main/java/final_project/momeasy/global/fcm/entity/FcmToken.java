package final_project.momeasy.global.fcm.entity;

import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.entity.BaseEntity;
import final_project.momeasy.common.enums.DeviceType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "fcm_token",
        indexes = {
                @Index(name = "idx_fcm_token_parent", columnList = "parent_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_parent_token",
                        columnNames = {"parent_id", "token"}
                )
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class FcmToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    @Column(nullable = false, length = 512)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private DeviceType deviceType;

    public void updateDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }
}
