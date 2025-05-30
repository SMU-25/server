package final_project.momeasy;

import final_project.momeasy.global.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@EnableConfigurationProperties(JwtProperties.class)
public class MomeasyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MomeasyApplication.class, args);
	}

}
