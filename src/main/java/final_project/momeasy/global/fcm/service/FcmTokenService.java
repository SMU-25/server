package final_project.momeasy.global.fcm.service;

import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.fcm.entity.FcmToken;
import final_project.momeasy.global.fcm.exception.FcmErrorCode;
import final_project.momeasy.global.fcm.exception.FcmException;
import final_project.momeasy.common.enums.DeviceType;
import final_project.momeasy.global.fcm.repository.FcmTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FcmTokenService {

    private final FcmTokenRepository tokenRepository;

    @Transactional
    public void saveToken(Parent parent, String token, DeviceType deviceType) {
        tokenRepository.findByToken(token).ifPresentOrElse(
                f -> {
                    if (f.getParent().getId().equals(parent.getId())) {
                        // 같은 parent + token → 업데이트
                        f.updateDeviceType(deviceType);
                    } else {
                        // 다른 parent가 쓰던 토큰 → 삭제 후 새로 저장
                        tokenRepository.delete(f);
                        tokenRepository.flush();
                        tokenRepository.save(FcmToken.builder()
                                .parent(parent)
                                .token(token)
                                .deviceType(deviceType)
                                .build());
                    }
                },
                () -> tokenRepository.save(
                        FcmToken.builder()
                                .parent(parent)
                                .token(token)
                                .deviceType(deviceType)
                                .build()
                )
        );
    }

    @Transactional
    public void deleteToken(Parent parent, String token) {
        FcmToken f = tokenRepository.findByParentIdAndToken(parent.getId(), token)
                .orElseThrow(() -> new FcmException(FcmErrorCode.TOKEN_NOT_FOUND));
        tokenRepository.delete(f);
    }

    @Transactional
    public void deleteTokenSilentlyIfExists(String token) {
        tokenRepository.findByToken(token).ifPresent(tokenRepository::delete);
    }

    @Transactional
    public void deleteTokenSilentlyIfExists(Long parentId, String token) {
        tokenRepository.findByParentIdAndToken(parentId, token)
                .ifPresent(tokenRepository::delete);
    }

    @Transactional(readOnly = true)
    public List<String> getTokenStringsByParent(Parent parent) {
        return tokenRepository.findAllByParentId(parent.getId())
                .stream()
                .map(FcmToken::getToken)
                .toList();
    }

    @Transactional
    public void deleteAllByParent(Parent parent) {
        tokenRepository.deleteAllByParentId(parent.getId());
    }
}
