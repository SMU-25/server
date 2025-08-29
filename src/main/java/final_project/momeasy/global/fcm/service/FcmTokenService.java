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
                    if (!f.getParent().getId().equals(parent.getId())) {
                        f.setParent(parent);
                    }
                    f.setDeviceType(deviceType);
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
        FcmToken f = tokenRepository.findByToken(token)
                .orElseThrow(() -> new FcmException(FcmErrorCode.TOKEN_NOT_FOUND));

        if (!f.getParent().getId().equals(parent.getId())) {
            throw new FcmException(FcmErrorCode.TOKEN_NOT_OWNED);
        }
        tokenRepository.delete(f);
    }

    @Transactional
    public void deleteTokenSilentlyIfExists(String token) {
        tokenRepository.findByToken(token).ifPresent(tokenRepository::delete);
    }

    @Transactional(readOnly = true)
    public List<String> getTokenStringsByParent(Parent parent) {
        return tokenRepository.findAllByParentId(parent.getId())
                .stream()
                .map(FcmToken::getToken)
                .toList();
    }
}
