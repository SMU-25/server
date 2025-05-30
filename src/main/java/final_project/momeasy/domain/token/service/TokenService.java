package final_project.momeasy.domain.token.service;

import final_project.momeasy.domain.token.entity.Token;
import final_project.momeasy.domain.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {

    private final TokenRepository tokenRepository;

    public void saveOrUpdate(String email, String token) {
        Token refreshToken = Token.builder()
                .email(email)
                .refreshToken(token)
                .build();

        tokenRepository.save(refreshToken);
    }

    public void deleteByEmail(String email) {
        tokenRepository.deleteByEmail(email);
    }

    @Transactional(readOnly = true)
    public Optional<Token> findByRefreshToken(String token) {
        return tokenRepository.findByRefreshToken(token);
    }
}
