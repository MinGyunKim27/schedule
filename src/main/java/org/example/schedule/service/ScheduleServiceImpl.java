package org.example.schedule.service;

import org.example.schedule.dto.ScheduleRequestDto;
import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.entity.Schedule;
import org.example.schedule.repository.ScheduleRepository;
import org.example.schedule.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository; // ← 추가!

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }


    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        Schedule schedule = new Schedule(dto.getTaskTitle(),dto.getTaskContents(), dto.getUserId(), dto.getPassword());

        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Optional<Schedule> scheduleOpt = scheduleRepository.findScheduleById(id);
        Schedule schedule = scheduleOpt.orElse(null);

        String userName = userRepository.findUserNameById(schedule.getUserId());

        return new ScheduleResponseDto(Objects.requireNonNull(schedule),userName);
    }

    @Override
    public int deleteSchedule(Long id) {
        return scheduleRepository.deleteSchedule(id);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedulesByConditions(String name, LocalDate updateDate,Long pageNo,Long pageSize) {
        return scheduleRepository.findSchedulesByConditions(name, updateDate,pageNo,pageSize);
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long id, String taskTitle,String taskContents, Long userId, String password) {
        if (taskTitle == null || taskContents == null || userId == null || password == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Task, userName and password is required.");
        }
        int updateRow = scheduleRepository.updateSchedule(id,taskTitle,taskContents,userId,password);

        if (updateRow == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exits id = " + id);
        }

        Schedule schedule = scheduleRepository.findScheduleById(id).orElse(null);
        String userName = userRepository.findUserNameById(schedule.getUserId());

        return new ScheduleResponseDto(schedule,userName);
    }

}
