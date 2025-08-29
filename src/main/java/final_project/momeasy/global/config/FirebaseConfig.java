package final_project.momeasy.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;

import java.io.InputStream;

@Slf4j
@Profile("prod") // 운영에서만 로드
@Configuration
@ConditionalOnProperty(prefix = "firebase", name = "enabled", havingValue = "true", matchIfMissing = false)
public class FirebaseConfig {

    // 값이 없으면 null 주입되도록 처리
    @Value("${firebase.credentials.location:#{null}}")
    private Resource serviceAccount; // file:/ 또는 classpath: 모두 지원

    @PostConstruct
    public void init() {
        try {
            if (!FirebaseApp.getApps().isEmpty()) {
                log.debug("[Firebase] already initialized. skip.");
                return;
            }

            // 키 없으면 스킵(죽이지 말고)
            if (serviceAccount == null || !serviceAccount.exists()) {
                log.warn("[Firebase] service account not found. Skip Firebase initialization.");
                return;
            }

            try (InputStream in = serviceAccount.getInputStream()) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(in))
                        .build();
                FirebaseApp.initializeApp(options);
                log.info("[Firebase] initialized successfully.");
            }
        } catch (Exception e) {
            // 실패해도 앱은 계속 뜨게
            log.warn("[Firebase] initialization failed. Skipping. cause={}", e.getMessage(), e);
        }
    }
}
