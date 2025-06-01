package final_project.momeasy.domain.home_cam.entity;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.parent.entity.Parent;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Homecam {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false, unique = true)
    private String serial_num;

    private String place;

    private String date;

    private String video_url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id",nullable = false)
    private Parent parent;

    @OneToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    public void setParent(Parent parent) {
        this.parent = parent;
        parent.getHomecams().add(this);
    }

    public void setChild(Child child) {
        this.child = child;
        child.setHomecam(this);
    }
}
