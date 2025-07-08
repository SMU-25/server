package final_project.momeasy.global.util.mail.dto;

public class MailDTO {
    public record EmailRequestDTO(
            String email
    ) {
    }

    public record VerificationRequestDTO(
            String email,
            String code
    ) {
    }
}
