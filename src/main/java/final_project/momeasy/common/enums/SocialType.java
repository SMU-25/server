package final_project.momeasy.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum SocialType {
    LOCAL, KAKAO, NAVER;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static SocialType fromString(String value) {
        return Arrays.stream(SocialType.values())
                .filter(v -> v.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 소셜 타입: " + value));
    }
}
