package org.example.schedule.repository;


import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository{

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        LocalDateTime now = LocalDateTime.now();

        Map<String,Object> parameters = new HashMap<>();
        parameters.put("task",schedule.getTask());
        parameters.put("password",schedule.getPassword());
        parameters.put("user_name",schedule.getUserName());
        parameters.put("created_at", now);
        parameters.put("updated_at", null);
        parameters.put("userId", 0);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(key.longValue(),schedule.getTask(),schedule.getUserName(),schedule.getPassword(),now,null);
    }

    @Override
    public List<ScheduleResponseDto> findAllMemos() {
        return List.of();
    }

    @Override
    public Schedule findMemoById(Long id) {
        return null;
    }
}
