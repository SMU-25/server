package final_project.momeasy.global.auth.service;

import final_project.momeasy.common.enums.SocialType;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.parent.exception.ParentErrorCode;
import final_project.momeasy.domain.parent.exception.ParentException;
import final_project.momeasy.domain.parent.repository.ParentRepository;
import final_project.momeasy.global.auth.exception.AuthErrorCode;
import final_project.momeasy.global.auth.exception.AuthException;
import final_project.momeasy.global.util.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final ParentRepository parentRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public void resetPassword(String email) {
        // 사용자 조회
        Parent parent = parentRepository.findByEmail(email)
                .orElseThrow(() -> new ParentException(ParentErrorCode.NOT_FOUND));

        if (parent.getSocialType() != SocialType.LOCAL) {
            throw new AuthException(AuthErrorCode.SOCIAL_USER_CANNOT_UPDATE_PASSWORD);
        }

        // 임시 비밀번호 생성
        String tempPassword = generateTempPassword(8);

        // 임시 비밀번호 메일 발송
        mailService.sendTempPassword(email, tempPassword);

        // 비번 인코딩해서 저장
        String encodedPassword = passwordEncoder.encode(tempPassword);
        parent.updatePassword(encodedPassword);
        parentRepository.save(parent);
    }


    // 임시 비밀번호 생성 메서드
    private String generateTempPassword(int length) {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = upper.toLowerCase();
        String digits = "0123456789";
        String special = "!@#$%^&*";
        String allChars = upper + lower + digits + special;

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        // 보안상 기본 보장되는 요소 한 글자씩 넣어줌
        sb.append(upper.charAt(random.nextInt(upper.length())));
        sb.append(lower.charAt(random.nextInt(lower.length())));
        sb.append(digits.charAt(random.nextInt(digits.length())));
        sb.append(special.charAt(random.nextInt(special.length())));

        for (int i = 4; i < length; i++) {
            sb.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        return sb.toString();
    }
}
