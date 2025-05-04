package final_project.momeasy.domain.illness.entity;

import final_project.momeasy.common.enums.IllnessType;
import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.child.entity.ChildIllness;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Illness {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IllnessType illness;

    @Column(nullable = false)
    private String type_name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "illness", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ChildIllness> childIllnesses = new ArrayList<>();

    public void addChild(Child child) {
        ChildIllness childIllness = ChildIllness.builder()
                .child(child)
                .illness(this)
                .build();

        this.childIllnesses.add(childIllness);
        child.getChildIllnesses().add(childIllness);
    }
}
