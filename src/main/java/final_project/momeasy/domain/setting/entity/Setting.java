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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean all_alarm;

    @Column(nullable = false)
    private Boolean care_alarm;

    @Column(nullable = false)
    private Boolean marketing_alarm;

    @OneToOne(fetch =FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    public void setParent(Parent parent) {
        this.parent = parent;
    }
}
