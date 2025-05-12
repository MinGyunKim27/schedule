package org.example.schedule.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private String taskTitle;
    private String taskContents;
    private Long userId;
    private String password;
}
