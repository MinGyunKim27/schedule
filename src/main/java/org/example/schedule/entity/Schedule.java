package org.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Schedule {

    @Setter
    private Long id;
    private String taskTitle;
    private String taskContents;
    private Long userId;
    private String password;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public Schedule(String taskTitle,String taskContents,Long userId,String password){
        this.taskTitle = taskTitle;
        this.taskContents = taskContents;
        this.userId = userId;
        this.password = password;
    }
}
