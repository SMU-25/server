package final_project.momeasy.domain.token.service;

import final_project.momeasy.domain.token.entity.Token;
import final_project.momeasy.domain.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.util.HexFormat;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {

    private final StringRedisTemplate redisTemplate;

    private static final Duration REFRESH_TOKEN_TTL = Duration.ofDays(30);
    private static final String EMAIL_KEY_PREFIX = "refresth-token:email:";
    private static final String INDEX_KEY_PREFIX = "refresth-token:index:";

    public void saveOrUpdate(String email, String token) {
        String prev = redisTemplate.opsForValue().get(generateRefreshTokenKeyByEmail(email));
        if (prev != null) {
            redisTemplate.delete(generateIndexKeyByHash(sha256(prev)));
        }
        redisTemplate.opsForValue().set(generateRefreshTokenKeyByEmail(email), token, REFRESH_TOKEN_TTL);
        redisTemplate.opsForValue().set(generateIndexKeyByHash(sha256(token)), email, REFRESH_TOKEN_TTL);
    }

    public void deleteByEmail(String email) {
        String key = generateRefreshTokenKeyByEmail(email);
        String current = redisTemplate.opsForValue().get(key);
        if (current != null) {
            redisTemplate.delete(generateIndexKeyByHash(sha256(current)));
        }
        redisTemplate.delete(key);
    }

    @Transactional(readOnly = true)
    public Optional<Token> findByRefreshToken(String token) {
        String email = redisTemplate.opsForValue().get(generateIndexKeyByHash(sha256(token)));
        if (email == null) return Optional.empty();

        String stored = redisTemplate.opsForValue().get(generateRefreshTokenKeyByEmail(email));
        if (stored == null || !stored.equals(token)) return Optional.empty();

        return Optional.of(
                Token.builder()
                        .email(email)
                        .refreshToken(token)
                        .build());
    }

    @Transactional(readOnly = true)
    public Optional<Token> findByEmail(String email) {
        String token = redisTemplate.opsForValue().get(generateRefreshTokenKeyByEmail(email));
        if (token == null) return Optional.empty();
        return Optional.of(
                Token.builder()
                        .email(email)
                        .refreshToken(token)
                        .build()
        );
    }

    private static String generateRefreshTokenKeyByEmail(String email) {
        return EMAIL_KEY_PREFIX + email;
    }

    private static String generateIndexKeyByHash(String tokenHash) {
        return INDEX_KEY_PREFIX + tokenHash;
    }

    private static String sha256(String value) {
        try {
            var md = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(md.digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
