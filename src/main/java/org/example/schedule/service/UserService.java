package org.example.schedule.service;

import org.example.schedule.dto.UserRequestDto;
import org.example.schedule.dto.UserResponseDto;
import org.example.schedule.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserResponseDto saveUser(UserRequestDto userRequestDto);
    List<UserResponseDto> findAllUsers();
    User findUserById(Long id);
    int updateUser(Long id, String email, String name);
    int deleteUser(Long id);

}
