package org.example.schedule.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * 일정 등록 및 수정 시 요청 데이터를 전달하기 위한 DTO 클래스.
 */
@Getter
public class ScheduleRequestDto {

    /**
     * 일정 제목 (필수)
     */
    @NotNull(message = "제목은 필수입니다.")
    private String taskTitle;

    /**
     * 일정 내용 (필수, 최대 200자 제한)
     */
    @NotNull(message = "내용은 필수입니다.")
    @Size(max = 200, message = "내용은 200자 이내여야 합니다.")
    private String taskContents;

    /**
     * 작성자 ID (필수)
     */
    @NotNull(message = "userId는 필수입니다.")
    private Long userId;

    /**
     * 비밀번호 (필수)
     */
    @NotNull(message = "비밀번호는 필수입니다.")
    private String password;
}
