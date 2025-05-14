package org.example.schedule.service;

import org.example.schedule.dto.UserRequestDto;
import org.example.schedule.dto.UserResponseDto;
import org.example.schedule.entity.User;
import org.example.schedule.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto saveUser(UserRequestDto dto) {
        User user = new User(dto.getEmail(),dto.getName());
        return userRepository.saveUser(user);
    }

    @Override
    public List<UserResponseDto> findAllUsers() {
        return userRepository.findAllUsers();
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserById(id).orElse(null);
    }
    @Transactional
    @Override
    public int updateUser(Long id, String email, String name) {
        return userRepository.updateUser(id,email,name);
    }

    @Override
    public int deleteUser(Long id) {
        return userRepository.deleteUser(id);
    }
}
