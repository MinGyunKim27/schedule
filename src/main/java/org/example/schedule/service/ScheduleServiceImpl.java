package org.example.schedule.service;

import org.example.schedule.dto.ScheduleRequestDto;
import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.entity.Schedule;
import org.example.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public List<ScheduleResponseDto> findAllSchedules() {
        return scheduleRepository.findAllSchedules();
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Optional<Schedule> scheduleOpt = scheduleRepository.findScheduleById(id);
        Schedule schedule = scheduleOpt.orElse(null);

        return new ScheduleResponseDto(Objects.requireNonNull(schedule));
    }

    @Override
    public int deleteSchedule(Long id) {
        return scheduleRepository.deleteSchedule(id);
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long id, String task, String userName, String password) {
        if (task == null || userName == null || password == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Task, userName and password is required.");
        }
        int updateRow = scheduleRepository.updateSchedule(id,task,userName,password);

        if (updateRow == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exits id = " + id);
        }

        Schedule schedule = scheduleRepository.findScheduleById(id).orElse(null);

        return new ScheduleResponseDto(schedule);
    }

}
