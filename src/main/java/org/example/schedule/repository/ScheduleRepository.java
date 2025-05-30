package org.example.schedule.repository;

import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.entity.Schedule;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findSchedulesByConditions(String name, LocalDate updateDate,Long pageNo,Long pageSize);

    Optional<Schedule> findScheduleById(Long id);

    int updateSchedule(Long id, String taskTitle,String taskContents, Long userId);

    int deleteSchedule(Long id);

   boolean isPasswordMatch(Long scheduleId, String password);

}
