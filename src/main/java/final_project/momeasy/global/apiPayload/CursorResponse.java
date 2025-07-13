package final_project.momeasy.global.apiPayload;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CursorResponse<T> {
    private List<T> content;
    private Long nextCursor;  // 마지막 알림 ID
    private boolean hasNext;
}
