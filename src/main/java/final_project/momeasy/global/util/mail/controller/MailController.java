package final_project.momeasy.global.util.mail.controller;

import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.util.mail.dto.MailDTO;
import final_project.momeasy.global.util.mail.service.MailService;
import final_project.momeasy.global.util.mail.verification.exception.VerificationCodeErrorCode;
import final_project.momeasy.global.util.mail.verification.exception.VerificationCodeException;
import final_project.momeasy.global.util.mail.verification.service.VerificationCodeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
@Tag(name = "Email", description = "메일 전송 API")
public class MailController {

    private final MailService mailService;
    private final VerificationCodeService verificationCodeService;

    @PostMapping("/verification-code")
    public CustomResponse<String> sendVerificationCode(@RequestBody MailDTO.EmailRequestDTO emailRequestDTO) {
        mailService.sendVerificationCode(emailRequestDTO.email());
        return CustomResponse.onSuccess(HttpStatus.OK, "인증코드 전송 완료");
    }

    @PostMapping("/verification-code/validation")
    public CustomResponse<String> verifyCode(@RequestBody MailDTO.VerificationRequestDTO emailRequestDTO) {
        boolean isValid = verificationCodeService.verifyCode(emailRequestDTO.email(), emailRequestDTO.code());

        if (!isValid) {
            log.warn("[ MailController ] email: {}, code: {}", emailRequestDTO.email(), emailRequestDTO.code());
            throw new VerificationCodeException(VerificationCodeErrorCode.CODE_MISMATCH);
        }

        return CustomResponse.onSuccess(HttpStatus.OK, "인증 완료");

    }

}
