package final_project.momeasy.global.auth.dto;

import final_project.momeasy.common.enums.Gender;
import final_project.momeasy.common.enums.SocialType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

@Slf4j
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
            } catch (Exception e) {
                log.warn("카카오 생일 파싱 실패: {}, {}", birthYear, birthDay);
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
    }),

    NAVER("naver", attributes -> {

        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        log.info("[ OAuthAttributes ] response map keys: {}", response.keySet());
        log.info("[ OAuthAttributes ] response name: {}", response.get("name"));

        String name = (String) response.get("name");
        String email = (String) response.get("email");
        String genderStr = (String) response.get("gender");
        String profileImageUrl = (String) response.get("profile_image");
        String birthYear = (String) response.get("birthyear");
        String birthDay = (String) response.get("birthday");

        // 생년월일
        LocalDate birthdate = null;
        if (birthYear != null && birthDay != null && birthDay.matches("\\d{2}-\\d{2}")) {
            try {
                String full = birthYear + "-" + birthDay; // 예: "1999-05-03"
                birthdate = LocalDate.parse(full, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception e) {
                log.warn("네이버 생일 파싱 실패: {}, {}", birthYear, birthDay);
            }
        }

        // 성별 변환
        Gender gender = null;
        if ("M".equalsIgnoreCase(genderStr)) {
            gender = Gender.MALE;
        } else if ("F".equalsIgnoreCase(genderStr)) {
            gender = Gender.FEMALE;
        } else {
            gender = null; // "U" 또는 못 받은 경우
        }

        return ParentProfile.builder()
                .name(name)
                .email(email)
                .profileImageUrl(profileImageUrl)
                .gender(gender)
                .birthdate(birthdate)
                .socialType(SocialType.NAVER)
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
