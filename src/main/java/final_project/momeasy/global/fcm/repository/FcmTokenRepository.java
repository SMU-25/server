package final_project.momeasy.global.fcm.repository;

import final_project.momeasy.global.fcm.entity.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {

    // 토큰 단독으로 조회 (다른 parent와 중복될 수 있으니 주의)
    Optional<FcmToken> findByToken(String token);

    // 특정 parent의 모든 토큰 조회
    List<FcmToken> findAllByParentId(Long parentId);

    // parentId + token 조합으로 조회 (안전하게 특정 부모 소유 토큰만 찾을 때)
    Optional<FcmToken> findByParentIdAndToken(Long parentId, String token);

    // parentId + token 조합으로 삭제
    void deleteByParentIdAndToken(Long parentId, String token);

    // parent의 모든 토큰 삭제 (로그아웃/탈퇴 시 편리)
    void deleteAllByParentId(Long parentId);
}
