package final_project.momeasy.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.InputStream;

@Profile("prod")   // ✅ 운영 환경에서만 로드됨
@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() throws Exception {
        if (!FirebaseApp.getApps().isEmpty()) return;

        try (InputStream in = getClass().getResourceAsStream("/firebase/firebase-service-account.json")) {
            if (in == null) {
                throw new IllegalStateException("firebase-service-account.json not found in resources");
            }
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(in))
                    .build();
            FirebaseApp.initializeApp(options);
        }
    }
}
