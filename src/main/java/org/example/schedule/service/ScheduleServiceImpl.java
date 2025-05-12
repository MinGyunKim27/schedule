package org.example.schedule.service;

import org.example.schedule.dto.ScheduleRequestDto;
import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.entity.Schedule;
import org.example.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }


    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        Schedule schedule = new Schedule(dto.getTask(), dto.getUserName(), dto.getPassword());

        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule() {
        return List.of();
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        return null;
    }
}
