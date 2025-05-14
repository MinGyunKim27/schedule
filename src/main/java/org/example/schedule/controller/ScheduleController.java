package org.example.schedule.controller;

import jakarta.validation.Valid;
import org.example.schedule.dto.ScheduleRequestDto;
import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.service.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 *  스케줄 관련 요청을 처리하는 ScheduleController 클래스 입니다
 */
@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    /**
     * 스케줄 서비스 의존성 주입
     */
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * 스케줄 등록 요청 처리
     *
     * @param dto TaskTitle, TaskContents, UserId, Password
     * @return ResponseEntity 객체 HttpStatus.OK
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody @Valid ScheduleRequestDto dto) {
        ScheduleResponseDto result = scheduleService.saveSchedule(dto);
        return ResponseEntity.ok(result);
    }

    /**
     * 스케쿨 조회 요청 처리
     *
     * @param name 사용자 이름
     * @param updatedDate 수정일
     * @param pageNo 페이지 넘버
     * @param pageSize 페이지 크기
     * @return ResponseEntity 객체 HttpStatus.OK
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedulesByConditions(
            @RequestParam(required = false) String name,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate updatedDate,
            @RequestParam(defaultValue = "0") Long pageNo,
            @RequestParam(defaultValue = "10") Long pageSize
    ) {
        List<ScheduleResponseDto> result = scheduleService.findAllSchedulesByConditions(name, updatedDate, pageNo, pageSize);
        return ResponseEntity.ok(result);
    }

    /**
     * 스케줄 조회 요청 처리
     *
     * @param id 태스크 아이디
     * @return ResponseEntity 객체 HttpStatus.OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {
        ScheduleResponseDto result = scheduleService.findScheduleById(id);
        return ResponseEntity.ok(result);
    }

    /**
     * 스케줄 삭제 요청 처리
     *
     * @param id 태스크 아이디
     * @return ResponseEntity 객체 HttpStatus.OK
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok("deleted");
    }

    /**
     * 스케줄 수정 요청 조회
     *
     * @param id 태스크 아이디
     * @param dto TaskTitle, TaskContents, UserId, Password
     * @return ResponseEntity 객체 HttpStatus.OK
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody @Valid ScheduleRequestDto dto) {
        ScheduleResponseDto result = scheduleService.updateSchedule(id, dto.getTaskTitle(), dto.getTaskContents(), dto.getUserId(), dto.getPassword());
        return ResponseEntity.ok(result);
    }
}
