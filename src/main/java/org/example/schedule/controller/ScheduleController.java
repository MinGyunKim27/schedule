package org.example.schedule.controller;

import jakarta.validation.Valid;
import org.example.schedule.dto.ScheduleRequestDto;
import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.service.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedules")
@Valid
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleResponseDto createSchedule(@RequestBody @Valid ScheduleRequestDto dto){
        return scheduleService.saveSchedule(dto);
    }

    @GetMapping
    public List<ScheduleResponseDto> findAllSchedulesByConditions(
            @RequestParam(required = false) String name,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate updatedDate,
            @RequestParam(defaultValue = "0") Long pageNo,
            @RequestParam(defaultValue = "10") Long pageSize
    ){
        return scheduleService.findAllSchedulesByConditions(name,updatedDate,pageNo,pageSize);
    }


    @GetMapping("/{id}")
    public ScheduleResponseDto findScheduleById(@PathVariable Long id){
        return scheduleService.findScheduleById(id);
    }

    @DeleteMapping("/{id}")
    public int deleteSchedule(@PathVariable Long id){
        return scheduleService.deleteSchedule(id);
    }

    @PatchMapping("/{id}")
    public ScheduleResponseDto updateSchedule(
            @PathVariable Long id,
            @RequestBody @Valid ScheduleRequestDto dto){
        return scheduleService.updateSchedule(id,dto.getTaskTitle(),dto.getTaskContents(),dto.getUserId(),dto.getPassword());
    }


}
