package org.example.schedule.service;


import org.example.schedule.dto.ScheduleRequestDto;
import org.example.schedule.dto.ScheduleResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto);
    List<ScheduleResponseDto> findAllSchedules();
    ScheduleResponseDto findScheduleById(Long id);
}
