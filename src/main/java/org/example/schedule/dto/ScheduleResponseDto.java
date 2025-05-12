package org.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.schedule.entity.Schedule;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String task;
    private String userName;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ScheduleResponseDto(Schedule schedule){
        this.id = schedule.getId();
        this.task = schedule.getTask();
        this.userName = schedule.getUserName();
        this.password = schedule.getPassword();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }
}
