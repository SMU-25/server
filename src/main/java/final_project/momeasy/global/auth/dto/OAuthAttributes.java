package final_project.momeasy.global.auth.dto;

import final_project.momeasy.common.enums.Gender;
import final_project.momeasy.common.enums.SocialType;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
public enum OAuthAttributes {

    KAKAO("kakao", attributes -> {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        String name = (String) kakaoAccount.get("name");
        String email = (String) kakaoAccount.get("email");
        String genderStr = (String) kakaoAccount.get("gender");
        String profileImageUrl = (String) kakaoAccount.get("profile_image_url");
        String birthYear = (String) kakaoAccount.get("birthyear");
        String birthDay = (String) kakaoAccount.get("birthday");

        // 생년월일
        LocalDate birthdate = null;
        if (birthYear != null && birthDay != null && birthDay.length() == 4) {
            try {
                String full = birthYear + "-" + birthDay.substring(0, 2) + "-" + birthDay.substring(2, 4);
                birthdate = LocalDate.parse(full, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception ignored) {
            }
        }

        // 성별 변환
        Gender gender = null;
        if ("male".equalsIgnoreCase(genderStr)) {
            gender = Gender.MALE;
        } else if ("female".equalsIgnoreCase(genderStr)) {
            gender = Gender.FEMALE;
        }

        return ParentProfile.builder()
                .name(name)
                .email(email)
                .profileImageUrl(profileImageUrl)
                .gender(gender)
                .birthdate(birthdate)
                .socialType(SocialType.KAKAO)
                .build();
    });

    private final String registrationId;
    private final Function<Map<String, Object>, ParentProfile> userProfileFactory;

    public static ParentProfile extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 OAuth Provider: " + registrationId))
                .userProfileFactory.apply(attributes);
    }
}
