package org.example.schedule.entity;

import java.time.LocalDateTime;

public class Schedule {
    int id;
    String task;
    String user_name;
    String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
