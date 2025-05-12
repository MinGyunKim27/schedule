package org.example.schedule.dto;

import org.example.schedule.entity.Schedule;

import java.time.LocalDate;

public class ScheduleResposeWithoutPasswordDto {
    private Long id;
    private String taskTitle;
    private String taskContents;
    private Long userId;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public ScheduleResposeWithoutPasswordDto(Schedule schedule){
        this.id = schedule.getId();
        this.taskTitle = schedule.getTaskTitle();
        this.taskContents = schedule.getTaskContents();
        this.userId = schedule.getUserId();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }
}
