package org.example.schedule.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class User {
    private Long id;
    private String email;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}