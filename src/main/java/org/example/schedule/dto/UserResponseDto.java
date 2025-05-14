package org.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.schedule.entity.User;

import java.time.LocalDate;

/**
 * 사용자 정보를 응답으로 반환하는 클래스.
 */
@Getter
@AllArgsConstructor
public class UserResponseDto {
    /**
     * 사용자 Id
     */
    private Long id;
    /**
     * 사용자 이메일
     */
    private String email;
    /**
     * 사용자 이름
     */
    private String name;
    /**
     * 사용자 생성일
     */
    private LocalDate createdAt;
    /**
     * 사용자 수정일
     */
    private LocalDate updatedAt;

    /**
     * User 객체를 받아서 Dto를 생성하는 생성자
     */
    public UserResponseDto(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}
