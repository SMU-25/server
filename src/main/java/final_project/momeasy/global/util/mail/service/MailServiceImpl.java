package final_project.momeasy.global.util.mail.service;

import final_project.momeasy.global.util.mail.verification.service.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final VerificationCodeService verificationCodeService;

    @Override
    public void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    @Override
    public void sendVerificationCode(String email) {
        String code = verificationCodeService.generateAndStoreCode(email);

        String subject = "[맘편해] 이메일 인증 코드입니다.";
        String body = "인증 코드: " + code + "\n(유효 시간: 5분)";

        sendMail(email, subject, body);
    }

    @Override
    public void sendTempPassword(String email, String tempPassword) {
        String subject = "[맘편해] 임시 비밀번호입니다.";
        String body = "임시 비밀번호: " + tempPassword + "\n로그인 후 마이페이지에서 꼭 비밀번호 재설정이 필요합니다.";

        sendMail(email, subject, body);

    }

}
