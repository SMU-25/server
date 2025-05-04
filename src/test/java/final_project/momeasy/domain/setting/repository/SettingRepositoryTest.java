package final_project.momeasy.domain.setting.repository;

import final_project.momeasy.common.enums.Gender;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.setting.entity.Setting;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SettingRepositoryTest {

    @Autowired
    private SettingRepository settingRepository;

    @Test
    @DisplayName("부모 ID로 설정을 찾을 수 있다")
    void findByParentId() {
        // given
        Parent parent = Parent.builder()
                .name("testParent")
                .loginId("loginId")
                .password("password")
                .email("test@example.com")
                .gender(Gender.BOY)
                .birthdate(LocalDate.of(1990, 1, 1))
                .build();

        Setting setting = Setting.builder()
                .all_alarm(true)
                .care_alarm(true)
                .marketing_alarm(false)
                .build();
        
        parent.setSetting(setting);

        // when
        Setting savedSetting = settingRepository.save(setting);
        Optional<Setting> foundSetting = settingRepository.findByParentId(parent.getId());

        // then
        assertThat(foundSetting).isPresent();
        assertThat(foundSetting.get().getId()).isEqualTo(savedSetting.getId());
        assertThat(foundSetting.get().getParent().getId()).isEqualTo(parent.getId());
        assertThat(foundSetting.get().getAll_alarm()).isEqualTo(true);
        assertThat(foundSetting.get().getCare_alarm()).isEqualTo(true);
        assertThat(foundSetting.get().getMarketing_alarm()).isEqualTo(false);
    }

    @Test
    @DisplayName("존재하지 않는 부모 ID로 설정을 찾으면 빈 Optional을 반환한다")
    void findByParentId_NotFound() {
        // given
        Long nonExistentParentId = 999L;

        // when
        Optional<Setting> foundSetting = settingRepository.findByParentId(nonExistentParentId);

        // then
        assertThat(foundSetting).isEmpty();
    }
} 