package org.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class User {
    private Long id;
    private String email;
    private String name;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public User(String email,String name) {
        this.email = email;
        this.name = name;
    }

}