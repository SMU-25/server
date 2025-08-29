package final_project.momeasy.global.fcm.repository;

import final_project.momeasy.global.fcm.entity.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
    Optional<FcmToken> findByToken(String token);
    List<FcmToken> findAllByParentId(Long parentId);
    void deleteByToken(String token);
}
