package final_project.momeasy.domain.announcement.service;

import final_project.momeasy.common.enums.AnnouncementType;
import final_project.momeasy.domain.announcement.dto.AnnouncementRequestDto;
import final_project.momeasy.domain.announcement.dto.AnnouncementResponseDto;
import final_project.momeasy.domain.parent.entity.Parent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnnouncementService {
    AnnouncementResponseDto create(Parent actor, AnnouncementRequestDto req);
    AnnouncementResponseDto update(Parent actor, Long announcementId, AnnouncementRequestDto req);
    void delete(Parent actor, Long announcementId);
    AnnouncementResponseDto get(Long announcementId);
    Page<AnnouncementResponseDto> list(AnnouncementType type, Pageable pageable);

    // 공지(NOTICE): 항상 푸시 알림
    void pushNotice(Long announcementId);

    // 설정에 따라 푸시 알림
    void pushEvent(Long announcementId);
}
