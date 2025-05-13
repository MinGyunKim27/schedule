package org.example.schedule.service;


import org.example.schedule.dto.ScheduleRequestDto;
import org.example.schedule.dto.ScheduleResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto);
    ScheduleResponseDto findScheduleById(Long id);
    ScheduleResponseDto updateSchedule(Long id,String taskTitle,String taskContents,Long userId,String password);
    int deleteSchedule(Long id);
    List<ScheduleResponseDto> findAllSchedulesByConditions(String name, LocalDate updateDate,Long pageNo,Long pageSize);
}
