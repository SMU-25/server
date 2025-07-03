package final_project.momeasy.global.auth.client;

import final_project.momeasy.common.enums.SocialType;
import final_project.momeasy.global.auth.dto.ParentProfile;

public interface SocialOAuthApiClient {
    boolean supports(SocialType provider);

    ParentProfile fetchProfile(String accessToken);
}
