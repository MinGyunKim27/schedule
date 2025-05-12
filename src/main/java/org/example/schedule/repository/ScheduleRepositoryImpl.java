package org.example.schedule.repository;


import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(key.longValue(),schedule.getTask(),schedule.getUserName(),schedule.getPassword(),now,null);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        {
            return jdbcTemplate.query("select * from schedule.schedule", scheduleResponseDtoRowMapper());
        }
    }

    @Override
    public Optional<Schedule> findScheduleById(Long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedule.schedule where id = ?", scheduleResponseDtoRowMapperV2(), id);

        return result.stream().findAny();
    }

    @Override
    public int updateSchedule(Long id, String task, String userName,String password) {
        return jdbcTemplate.update("update schedule.schedule set task = ?, user_name = ? where password = ? and id = ?",task,userName,password,id);
    }

    @Override
    public int deleteSchedule(Long id) {
        return jdbcTemplate.update("delete from schedule.schedule where id = ?" , id);

    }

    private RowMapper<ScheduleResponseDto> scheduleResponseDtoRowMapper(){
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("task"),
                        rs.getString("user_name"),
                        rs.getString("password"),
                        Optional.ofNullable(rs.getTimestamp("created_at"))
                                .map(Timestamp::toLocalDateTime)
                                .orElse(null),
                        Optional.ofNullable(rs.getTimestamp("updated_at"))
                                .map(Timestamp::toLocalDateTime)
                                .orElse(null)
                );
            }
        };
    }

    private RowMapper<Schedule> scheduleResponseDtoRowMapperV2(){
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("task"),
                        rs.getString("user_name"),
                        rs.getString("password"),
                        //Timestamp 자료 형 LocalDateTime으로 변환
                        Optional.ofNullable(rs.getTimestamp("created_at"))
                                .map(Timestamp::toLocalDateTime)
                                .orElse(null),
                        Optional.ofNullable(rs.getTimestamp("updated_at"))
                                .map(Timestamp::toLocalDateTime)
                                .orElse(null)
                );
            }
        };
    }
}
