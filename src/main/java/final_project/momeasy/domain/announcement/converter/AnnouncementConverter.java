package final_project.momeasy.domain.announcement.converter;

import final_project.momeasy.domain.announcement.dto.AnnouncementRequestDto;
import final_project.momeasy.domain.announcement.dto.AnnouncementResponseDto;
import final_project.momeasy.domain.announcement.entity.Announcement;

public class AnnouncementConverter {

    public static Announcement toEntity(AnnouncementRequestDto req, String createdBy) {
        return Announcement.draft(
                req.getType(),
                req.getTitle(),
                req.getContent(),
                Boolean.TRUE.equals(req.getPinned()),
                createdBy
        );
    }

    public static void apply(Announcement entity, AnnouncementRequestDto req) {
        entity.update(
                req.getTitle(),
                req.getContent(),
                req.getPinned(),
                req.getType()
        );
    }

    public static AnnouncementResponseDto toResponse(Announcement a) {
        return new AnnouncementResponseDto(
                a.getId(),
                a.getType(),
                a.getTitle(),
                a.getContent(),
                a.isPinned(),
                a.getCreatedBy(),
                a.getCreatedAt(),
                a.getUpdatedAt()
        );
    }
}
