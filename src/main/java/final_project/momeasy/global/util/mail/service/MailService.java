package final_project.momeasy.global.util.mail.service;

public interface MailService {

    void sendMail(String to, String subject, String body);

    void sendVerificationCode(String email);
}
