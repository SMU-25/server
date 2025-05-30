package final_project.momeasy.domain.token.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Token {

    @Id @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String refreshToken;

}
