package final_project.momeasy.global.util.mail.verification.service;

public interface VerificationCodeService {

    boolean verifyCode(String email, String code);

    String generateAndStoreCode(String email);

    void markAsVerified(String email);

    boolean isVerified(String email);
}
