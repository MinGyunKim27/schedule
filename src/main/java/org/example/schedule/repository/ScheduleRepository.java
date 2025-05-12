package org.example.schedule.repository;

import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.entity.Schedule;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllMemos();

    Schedule findMemoById(Long id);

}
