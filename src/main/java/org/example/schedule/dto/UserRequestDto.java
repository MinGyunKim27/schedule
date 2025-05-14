package org.example.schedule.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 *
 */
@Getter
public class UserRequestDto {
    /**
     * 사용자 이메일(형식 맞춰서)
     */
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    /**
     * 사용자 이름(빈칸 안됨)
     */
    @NotBlank
    private String name;
}
