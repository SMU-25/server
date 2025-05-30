package final_project.momeasy.domain.token.repository;

import final_project.momeasy.domain.token.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findByRefreshToken(String token);

    void deleteByEmail(String email);
}
