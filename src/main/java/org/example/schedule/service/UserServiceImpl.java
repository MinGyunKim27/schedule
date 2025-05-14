package org.example.schedule.service;

import org.example.schedule.dto.UserRequestDto;
import org.example.schedule.dto.UserResponseDto;
import org.example.schedule.entity.User;
import org.example.schedule.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 사용자 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // 생성자를 통해 의존성 주입
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 사용자 정보를 저장합니다.
     * @param dto 사용자 요청 데이터 (email, name)
     * @return 저장된 사용자 정보 (id 포함)
     */
    @Override
    public UserResponseDto saveUser(UserRequestDto dto) {
        // 요청 DTO를 엔티티로 변환
        User user = new User(dto.getEmail(), dto.getName());
        // 저장 후 결과 반환
        return userRepository.saveUser(user);
    }

    /**
     * 전체 사용자 목록을 조회합니다.
     * @return 사용자 응답 DTO 리스트
     */
    @Override
    public List<UserResponseDto> findAllUsers() {
        return userRepository.findAllUsers();
    }

    /**
     * 특정 사용자 ID로 사용자 정보를 조회합니다.
     * @param id 사용자 ID
     * @return User 객체 또는 null (존재하지 않는 경우)
     */
    @Override
    public User findUserById(Long id) {
        return userRepository.findUserById(id).orElse(null);
    }

    /**
     * 사용자 정보를 수정합니다.
     * @param id 사용자 ID
     * @param email 수정할 이메일
     * @param name 수정할 이름
     * @return 반영된 행 수 (성공 시 1)
     */
    @Transactional
    @Override
    public int updateUser(Long id, String email, String name) {
        return userRepository.updateUser(id, email, name);
    }

    /**
     * 사용자 정보를 삭제합니다.
     * @param id 사용자 ID
     * @return 반영된 행 수 (성공 시 1)
     */
    @Override
    public int deleteUser(Long id) {
        return userRepository.deleteUser(id);
    }
}
