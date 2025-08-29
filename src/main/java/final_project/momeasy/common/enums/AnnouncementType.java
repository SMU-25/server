package final_project.momeasy.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum AnnouncementType {
    NOTICE("공지사항"),
    EVENT("이벤트");

    private final String description;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static AnnouncementType fromString(String value) {
        return Arrays.stream(AnnouncementType.values())
                .filter(v -> v.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 공지 타입: " + value));
    }
}
