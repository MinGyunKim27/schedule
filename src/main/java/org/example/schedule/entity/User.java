package org.example.schedule.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class User {
    private int id;
    private String email;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
