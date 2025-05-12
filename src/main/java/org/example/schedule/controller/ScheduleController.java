package org.example.schedule.controller;

import org.example.schedule.dto.ScheduleRequestDto;
import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto dto){
        return scheduleService.saveSchedule(dto);
    }

    @GetMapping
    public List<ScheduleResponseDto> findAllSchedules(){
        return scheduleService.findAllSchedules();
    }

    @GetMapping("/{id}")
    public ScheduleResponseDto findScheduleById(@PathVariable Long id){
        return scheduleService.findScheduleById(id);
    }
}
