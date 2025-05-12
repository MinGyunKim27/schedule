package org.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    @Setter
    private Long id;
    private String task;
    private String userName;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Schedule(String task,String userName,String password){
        this.task = task;
        this.userName = userName;
        this.password = password;
    }
}
