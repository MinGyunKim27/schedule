package org.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.schedule.entity.User;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;
    private String name;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public UserResponseDto(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}
