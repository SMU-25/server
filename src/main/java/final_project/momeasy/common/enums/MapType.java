package final_project.momeasy.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MapType {

    HP8("병원"),
    PM9("약국");

    private final String category;
}
