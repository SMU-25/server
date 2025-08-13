package final_project.momeasy.domain.setting.repository;

import final_project.momeasy.domain.setting.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettingRepository extends JpaRepository<Setting, Long> {

    Optional<Setting> findByParentId(Long parentId);
}
