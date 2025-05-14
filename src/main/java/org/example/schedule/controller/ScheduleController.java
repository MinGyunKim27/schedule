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

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody @Valid ScheduleRequestDto dto) {
        ScheduleResponseDto result = scheduleService.saveSchedule(dto);
        return ResponseEntity.ok(result); // 또는 status(HttpStatus.CREATED).body(result)
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {
        ScheduleResponseDto result = scheduleService.findScheduleById(id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok("deleted");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody @Valid ScheduleRequestDto dto) {
        ScheduleResponseDto result = scheduleService.updateSchedule(id, dto.getTaskTitle(), dto.getTaskContents(), dto.getUserId(), dto.getPassword());
        return ResponseEntity.ok(result);
    }
}
