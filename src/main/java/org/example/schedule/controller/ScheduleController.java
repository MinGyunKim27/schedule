package org.example.schedule.controller;

import org.example.schedule.dto.ScheduleRequestDto;
import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.service.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/conditions")
    public List<ScheduleResponseDto> findAllSchedulesByConditions(
            @RequestParam(required = false) String name,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate updatedDate
    ){
        return scheduleService.findAllSchedulesByConditions(name,updatedDate);
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
            @RequestBody ScheduleRequestDto dto){
        return scheduleService.updateSchedule(id,dto.getTaskTitle(),dto.getTaskContents(),dto.getUserId(),dto.getPassword());
    }


}
