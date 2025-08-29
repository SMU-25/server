package final_project.momeasy.domain.announcement.entity;

import final_project.momeasy.common.enums.AnnouncementType;
import final_project.momeasy.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "announcement")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Announcement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AnnouncementType type;

    @Column(nullable = false, length = 200)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean pinned;          // 상단 고정

    @Column(nullable = false)
    private boolean deleted;         // 소프트 삭제

    @Column(nullable = false, length = 120)
    private String createdBy;

    // 정적 팩토리
    public static Announcement draft(AnnouncementType type, String title, String content, boolean pinned, String createdBy) {
        return new Announcement(
                null, type, title, content, pinned, false, createdBy
        );
    }


    public void update(String title, String content, Boolean pinned, AnnouncementType type) {
        if (title != null)  this.title = title;
        if (content != null) this.content = content;
        if (pinned != null) this.pinned = pinned;
        if (type != null)   this.type = type;
    }

    public void softDelete() { this.deleted = true; }
}
