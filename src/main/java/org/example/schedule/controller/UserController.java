package org.example.schedule.controller;

import org.example.schedule.dto.UserRequestDto;
import org.example.schedule.dto.UserResponseDto;
import org.example.schedule.entity.User;
import org.example.schedule.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto dto){
        return userService.saveUser(dto);
    }

    @GetMapping
    public List<UserResponseDto> findAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable Long id){
        return userService.findUserById(id);

    }

    @DeleteMapping("/{id}")
    public int deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
}
