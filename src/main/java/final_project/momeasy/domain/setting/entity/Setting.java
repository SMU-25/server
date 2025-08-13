package final_project.momeasy.domain.setting.entity;

import final_project.momeasy.domain.parent.entity.Parent;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean all_alarm;

    @Column(nullable = false)
    private Boolean care_alarm;

    @Column(nullable = false)
    private Boolean marketing_alarm;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    // 연관관계 설정
    public void setParent(Parent parent) {
        this.parent = parent;
    }

    // 개별 설정 setter
    public void setAll_alarm(Boolean all_alarm) {
        this.all_alarm = all_alarm;
    }

    public void setCare_alarm(Boolean care_alarm) {
        this.care_alarm = care_alarm;
    }

    public void setMarketing_alarm(Boolean marketing_alarm) {
        this.marketing_alarm = marketing_alarm;
    }

    // 기본 설정 생성
    public static Setting createDefault() {
        return Setting.builder()
                .all_alarm(true)
                .care_alarm(true)
                .marketing_alarm(true)
                .build();
    }
}
