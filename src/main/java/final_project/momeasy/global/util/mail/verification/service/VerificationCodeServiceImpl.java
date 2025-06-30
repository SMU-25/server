package final_project.momeasy.global.util.mail.verification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final RedisTemplate<String, String> redisTemplate;

    private String generateCode() {
        return RandomStringUtils.randomNumeric(6);
    }

    @Override
    public boolean verifyCode(String email, String code) {
        String stored = redisTemplate.opsForValue().get(email);
        if (!code.equals(stored)) {
            return false;
        }
        markAsVerified(email);
        return true;
    }

    @Override
    public String generateAndStoreCode(String email) {
        String code = generateCode();
        redisTemplate.opsForValue().set(email, code, 5, TimeUnit.MINUTES);
        log.info("Stored verification code {}", code);
        return code;
    }

    @Override
    public void markAsVerified(String email) {
        redisTemplate.opsForValue().set("verified:" + email, "true", Duration.ofMinutes(30));
    }

    @Override
    public boolean isVerified(String email) {
        String result = redisTemplate.opsForValue().get("verified:" + email);
        return "true".equals(result);
    }
}
