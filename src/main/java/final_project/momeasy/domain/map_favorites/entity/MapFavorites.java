package final_project.momeasy.domain.map_favorites.entity;

import final_project.momeasy.common.enums.MapType;
import final_project.momeasy.domain.parent.entity.Parent;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MapFavorites {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String place_name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MapType category_group_code;

    private String category_name;

    private String category_group_name;

    private String phone;

    private String address_name;

    private String road_address_name;

    private String place_url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id",nullable = false)
    private Parent parent;

    public void setParent(Parent parent) {
        this.parent = parent;
        parent.getMapFavorites().add(this);
    }
}
