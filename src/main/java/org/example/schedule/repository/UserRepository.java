package org.example.schedule.repository;

import org.example.schedule.dto.UserResponseDto;
import org.example.schedule.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {
    UserResponseDto saveUser(User user);
    List<UserResponseDto> findAllUsers();

    Optional<User> findUserById(Long id);

    int updateUser(Long id, String email , String name);

    int deleteUser(Long id);

    String findUserNameById(Long userId);


}
