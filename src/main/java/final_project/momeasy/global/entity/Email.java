package final_project.momeasy.global.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Email {
    @Id @GeneratedValue
    private Long id;

    private int verify_num;

    private Boolean is_verified;
}
