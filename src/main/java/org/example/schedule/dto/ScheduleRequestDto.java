package org.example.schedule.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    @NotNull(message = "제목은 필수입니다.")
    private String taskTitle;

    @NotNull(message = "내용은 필수입니다.")
    @Size(max = 200, message = "내용은 200자 이내여야 합니다.")
    private String taskContents;

    @NotNull(message = "userId는 필수입니다.")
    private Long userId;

    @NotNull(message = "비밀번호는 필수입니다.")
    private String password;
}
