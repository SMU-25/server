package final_project.momeasy.domain.announcement.service;

import final_project.momeasy.common.enums.AnnouncementType;
import final_project.momeasy.domain.announcement.converter.AnnouncementConverter;
import final_project.momeasy.domain.announcement.dto.AnnouncementRequestDto;
import final_project.momeasy.domain.announcement.dto.AnnouncementResponseDto;
import final_project.momeasy.domain.announcement.entity.Announcement;
import final_project.momeasy.domain.announcement.exception.AnnouncementErrorCode;
import final_project.momeasy.domain.announcement.exception.AnnouncementException;
import final_project.momeasy.domain.announcement.repository.AnnouncementRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.fcm.service.FcmService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository repository;
    private final FcmService fcmService;

    @Override
    public AnnouncementResponseDto create(Parent actor, AnnouncementRequestDto req) {
        Announcement saved = repository.save(AnnouncementConverter.toEntity(req, actor.getEmail()));

        // 자동 푸시: 타입별 토픽으로 즉시 브로드캐스트
        if (saved.getType() == AnnouncementType.NOTICE) {
            pushNotice(saved.getId());
        } else if (saved.getType() == AnnouncementType.EVENT) {
            pushEvent(saved.getId());
        }
        return AnnouncementConverter.toResponse(saved);
    }

    @Override
    public AnnouncementResponseDto update(Parent actor, Long id, AnnouncementRequestDto req) {
        Announcement a = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new AnnouncementException(AnnouncementErrorCode.NOT_FOUND));
        AnnouncementConverter.apply(a, req);
        return AnnouncementConverter.toResponse(a);
    }

    @Override
    public void delete(Parent actor, Long id) {
        Announcement a = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new AnnouncementException(AnnouncementErrorCode.NOT_FOUND));
        a.softDelete();
    }

    @Transactional(readOnly = true)
    @Override
    public AnnouncementResponseDto get(Long id) {
        Announcement a = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new AnnouncementException(AnnouncementErrorCode.NOT_FOUND));
        return AnnouncementConverter.toResponse(a);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AnnouncementResponseDto> list(AnnouncementType type, Pageable pageable) {
        Specification<Announcement> spec = (root, q, cb) -> {
            var ps = new ArrayList<Predicate>();
            ps.add(cb.isFalse(root.get("deleted")));
            if (type != null) ps.add(cb.equal(root.get("type"), type));
            return cb.and(ps.toArray(new Predicate[0]));
        };
        return repository.findAll(spec, pageable).map(AnnouncementConverter::toResponse);
    }

    @Override
    public void pushNotice(Long announcementId) {
        Announcement a = repository.findByIdAndDeletedFalse(announcementId)
                .orElseThrow(() -> new AnnouncementException(AnnouncementErrorCode.NOT_FOUND));
        if (a.getType() != AnnouncementType.NOTICE) {
            throw new AnnouncementException(AnnouncementErrorCode.INVALID_REQUEST);
        }
        String deeplink = "/announcements/" + a.getId();
        // 설정에 관계없이 항상 Push 알림으로 전송
        fcmService.sendAnnouncementToTopic("notice", a.getTitle(), a.getContent(), deeplink, a.getType().name());
    }

    @Override
    public void pushEvent(Long announcementId) {
        Announcement a = repository.findByIdAndDeletedFalse(announcementId)
                .orElseThrow(() -> new AnnouncementException(AnnouncementErrorCode.NOT_FOUND));
        if (a.getType() != AnnouncementType.EVENT) {
            throw new AnnouncementException(AnnouncementErrorCode.INVALID_REQUEST);
        }
        String deeplink = "/announcements/" + a.getId();
        // 설정에 따라 인앱 알림, 푸시 알림
        fcmService.sendAnnouncementToTopic("marketing", a.getTitle(), a.getContent(), deeplink, a.getType().name());
    }
}
