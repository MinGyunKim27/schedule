package org.example.schedule.service;

import org.example.schedule.dto.ScheduleRequestDto;
import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.entity.Schedule;
import org.example.schedule.exception.PasswordMismatchException;
import org.example.schedule.repository.ScheduleRepository;
import org.example.schedule.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 일정 관련 비즈니스 로직을 담당하는 서비스 구현 클래스입니다.
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    // 생성자를 통한 의존성 주입
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    /**
     * 일정 등록 기능
     * @param dto 요청 DTO (taskTitle, taskContents, userId, password)
     * @return 생성된 일정에 대한 응답 DTO
     */
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        Schedule schedule = new Schedule(
                dto.getTaskTitle(),
                dto.getTaskContents(),
                dto.getUserId(),
                dto.getPassword()
        );
        return scheduleRepository.saveSchedule(schedule);
    }

    /**
     * 특정 ID로 단건 일정 조회
     * @param id 조회할 일정의 ID
     * @return 일정 정보 + 작성자 이름을 포함한 DTO
     */
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Optional<Schedule> scheduleOpt = scheduleRepository.findScheduleById(id);
        Schedule schedule = scheduleOpt.orElse(null);

        // 작성자 이름을 별도로 조회
        String userName = userRepository.findUserNameById(schedule.getUserId());

        return new ScheduleResponseDto(Objects.requireNonNull(schedule), userName);
    }

    /**
     * 일정 삭제
     * @param id 삭제할 일정 ID
     * @return 삭제된 row 수
     */
    @Override
    public int deleteSchedule(Long id) {
        return scheduleRepository.deleteSchedule(id);
    }

    /**
     * 조건(작성자명, 수정일)과 페이지네이션 기반으로 일정 목록 조회
     * @param name 작성자 이름
     * @param updateDate 수정일
     * @param pageNo 페이지 번호
     * @param pageSize 페이지 크기
     * @return 조건에 맞는 일정 목록
     */
    @Override
    public List<ScheduleResponseDto> findAllSchedulesByConditions(String name, LocalDate updateDate, Long pageNo, Long pageSize) {
        return scheduleRepository.findSchedulesByConditions(name, updateDate, pageNo, pageSize);
    }

    /**
     * 일정 수정
     * @param id 수정할 일정 ID
     * @param taskTitle 변경할 제목
     * @param taskContents 변경할 내용
     * @param userId 작성자 ID
     * @param password 비밀번호 확인용
     * @return 수정된 일정 정보
     */
    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, String taskTitle, String taskContents, Long userId, String password) {
        // 필수값 유효성 검증
        if (taskTitle == null || taskContents == null || userId == null || password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task, userName and password is required.");
        }

        // 비밀번호 일치 여부 확인
        if (!scheduleRepository.isPasswordMatch(id, password)) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        // DB 업데이트 실행
        int updateRow = scheduleRepository.updateSchedule(id, taskTitle, taskContents, userId);

        // 업데이트 실패 시 예외 처리
        if (updateRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id does not exist = " + id);
        }

        // 수정된 일정 정보 재조회 후 반환
        Schedule schedule = scheduleRepository.findScheduleById(id).orElse(null);
        String userName = userRepository.findUserNameById(schedule.getUserId());

        return new ScheduleResponseDto(schedule, userName);
    }
}
