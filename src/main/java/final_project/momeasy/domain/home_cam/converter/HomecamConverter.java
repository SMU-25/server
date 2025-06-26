package final_project.momeasy.domain.home_cam.converter;

import final_project.momeasy.domain.home_cam.dto.HomecamRequestDTO;
import final_project.momeasy.domain.home_cam.dto.HomecamResponseDTO;
import final_project.momeasy.domain.home_cam.entity.Homecam;
import final_project.momeasy.domain.parent.entity.Parent;

public class HomecamConverter {
    public static HomecamResponseDTO.HomecamDTO toHomecamDTO(Homecam homecam) {
        return HomecamResponseDTO.HomecamDTO.builder()
                .name(homecam.getName())
                .place(homecam.getPlace())
                .createdAt(homecam.getCreatedAt())
                .build();
    }

    public static HomecamResponseDTO.HomecamVideoDTO toHomecamVideoDTO(Homecam homecam) {
        return HomecamResponseDTO.HomecamVideoDTO.builder()
                .video_url(homecam.getVideo_url())
                .build();
    }

    public static Homecam toHomecam(HomecamRequestDTO.HomecamCreateDTO homecamRegisterDTO, Parent parent) {
        return Homecam.builder()
                .name(homecamRegisterDTO.getName())
                .serialNum(homecamRegisterDTO.getSerial_num())
                .place(homecamRegisterDTO.getPlace())
                .parent(parent)
                .build();
    }
}
