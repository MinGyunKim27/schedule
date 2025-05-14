package org.example.schedule.controller;

import jakarta.validation.Valid;
import org.example.schedule.dto.UserRequestDto;
import org.example.schedule.dto.UserResponseDto;
import org.example.schedule.entity.User;
import org.example.schedule.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 사용자 관련 요청을 처리하는 컨트롤러 클래스입니다.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    /**
     * 사용자 서비스 의존성 주입
     */
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 사용자 등록 요청을 처리합니다.
     *
     * @param dto 사용자 이름, 이메일
     * @return ResponseEntity 객체(HttpStatus.OK)
     */
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto dto) {
        UserResponseDto result = userService.saveUser(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 전체 사용자 목록을 조회합니다.
     *
     * @return ResponseEntity 객체(HttpStatus.OK)
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAllUsers() {
        List<UserResponseDto> result = userService.findAllUsers();
        return ResponseEntity.ok(result);
    }

    /**
     * 사용자 ID로 사용자 정보를 조회합니다.
     *
     * @param id 사용자 ID
     * @return ResponseEntity 객체(HttpStatus.OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        User result = userService.findUserById(id);
        return ResponseEntity.ok(result);
    }

    /**
     * 사용자 ID로 사용자 정보를 삭제합니다.
     *
     * @param id 사용자 ID
     * @return ResponseEntity 객체(HttpStatus.OK)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("deleted");
    }
}
