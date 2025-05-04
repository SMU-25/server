package final_project.momeasy.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Relation {
    MOM("엄마"),
    DAD("아빠");

    private final String relation;
}
