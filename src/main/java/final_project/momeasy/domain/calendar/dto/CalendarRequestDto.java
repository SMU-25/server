package final_project.momeasy.domain.calendar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CalendarRequestDto {

    @NotNull(message = "날짜는 필수입니다.")
    private LocalDate scheduleDate;

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    private String content;

}
