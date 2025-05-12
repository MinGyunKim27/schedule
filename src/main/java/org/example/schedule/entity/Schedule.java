package org.example.schedule.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Schedule {
    private Long id;
    private String task;
    private String userName;
    private String password;
    private int userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Schedule(String task,String userName,String password){
        this.task = task;
        this.userName = userName;
        this.password = password;
    }
}
