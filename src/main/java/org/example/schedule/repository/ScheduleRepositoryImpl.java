package org.example.schedule.repository;


import org.example.schedule.dto.ScheduleResponseDto;
import org.example.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    private final UserRepository userRepository; // ← 추가!

    public ScheduleRepositoryImpl(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("schedule")
                .usingGeneratedKeyColumns("id");

        LocalDate now = LocalDate.now();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("taskTitle", schedule.getTaskTitle());
        parameters.put("taskContents", schedule.getTaskContents());
        parameters.put("password", schedule.getPassword());
        parameters.put("userId", schedule.getUserId());
        parameters.put("created_at", now);
        parameters.put("updated_at", null);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        String userName = userRepository.findUserNameById(schedule.getUserId());

        return new ScheduleResponseDto(
                key.longValue(),
                schedule.getTaskTitle(),
                schedule.getTaskContents(),
                userName,
                schedule.getPassword(),
                now,
                null
        );
    }

    @Override
    public List<ScheduleResponseDto> findSchedulesByConditions(String name, LocalDate updatedDate,Long pageNo,Long pageSize) {
        StringBuilder sql = new StringBuilder("""
            SELECT s.id, s.taskTitle, s.taskContents, s.password,
                   s.created_at, s.updated_at,
                   u.name AS user_name
            FROM schedule s
            JOIN user u ON s.userId = u.id
        """);

        List<Object> params = new ArrayList<>();
        List<String> conditions = new ArrayList<>();

        if (name != null && !name.isBlank()) {
            conditions.add("u.name = ?");
            params.add(name);
        }
        if (updatedDate != null) {
            conditions.add("DATE(s.updated_at) = ?");
            params.add(Date.valueOf(updatedDate));
        }

        if (!conditions.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", conditions));
        }

        sql.append(" ORDER BY s.updated_at DESC");

        if (pageNo != null && pageSize != null) {
            sql.append(" LIMIT ? OFFSET ?");
            params.add(pageSize.intValue());
            params.add((int)(pageNo * pageSize));
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), scheduleResponseDtoRowMapper());
    }


    @Override
    public Optional<Schedule> findScheduleById(Long id) {
        List<Schedule> result = jdbcTemplate.query(
                "SELECT * FROM schedule WHERE id = ?",
                scheduleEntityRowMapper(),
                id
        );
        return result.stream().findAny();
    }

    @Override
    public int updateSchedule(Long id, String taskTitle, String taskContents, Long userId, String password) {
        LocalDateTime now = LocalDateTime.now();
        return jdbcTemplate.update("""
            UPDATE schedule
            SET taskTitle = ?, taskContents = ?, userId = ?, updated_at = ?
            WHERE password = ? AND id = ?
        """, taskTitle, taskContents, userId, now, password, id);
    }

    @Override
    public int deleteSchedule(Long id) {
        return jdbcTemplate.update("DELETE FROM schedule WHERE id = ?", id);
    }



    // Response DTO 매핑
    private RowMapper<ScheduleResponseDto> scheduleResponseDtoRowMapper() {
        return (rs, rowNum) -> new ScheduleResponseDto(
                rs.getLong("id"),
                rs.getString("taskTitle"),
                rs.getString("taskContents"),
                rs.getString("user_name"),
                rs.getString("password"),
                toLocalDate(rs.getTimestamp("created_at")),
                toLocalDate(rs.getTimestamp("updated_at"))
        );
    }

    // Entity 매핑
    private RowMapper<Schedule> scheduleEntityRowMapper() {
        return (rs, rowNum) -> new Schedule(
                rs.getLong("id"),
                rs.getString("taskTitle"),
                rs.getString("taskContents"),
                rs.getLong("userId"),
                rs.getString("password"),
                toLocalDate(rs.getTimestamp("created_at")),
                toLocalDate(rs.getTimestamp("updated_at"))
        );
    }

    // LocalDate 변환 헬퍼
    private LocalDate toLocalDate(Timestamp timestamp) {
        return Optional.ofNullable(timestamp)
                .map(Timestamp::toLocalDateTime)
                .map(LocalDateTime::toLocalDate)
                .orElse(null);
    }
}
