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

/**
 * ScheduleRepository 구현체로, JDBC 기반의 일정 관련 DB 처리를 담당.
 */
@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    /**
     * 생성자 의존성 주입
     */
    public ScheduleRepositoryImpl(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    /**
     * 일정 저장 - Schedule 엔티티를 DB에 insert 하고 생성된 ID를 반환.
     * @param schedule 저장할 일정 정보
     * @return ScheduleResponseDto (생성된 일정 정보 포함)
     */
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

    /**
     * 조건 기반 일정 목록 조회 (이름, 수정일, 페이징)
     * @param name 작성자 이름
     * @param updatedDate 수정일
     * @param pageNo 페이지 번호
     * @param pageSize 페이지 크기
     * @return 조건에 맞는 일정 목록
     */
    @Override
    public List<ScheduleResponseDto> findSchedulesByConditions(String name, LocalDate updatedDate, Long pageNo, Long pageSize) {
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

    /**
     * ID 기반 단건 일정 조회
     * @param id 일정 ID
     * @return Optional<Schedule>
     */
    @Override
    public Optional<Schedule> findScheduleById(Long id) {
        List<Schedule> result = jdbcTemplate.query(
                "SELECT * FROM schedule WHERE id = ?",
                scheduleEntityRowMapper(),
                id
        );
        return result.stream().findAny();
    }

    /**
     * 일정 업데이트
     * @param id 일정 ID
     * @param taskTitle 제목
     * @param taskContents 내용
     * @param userId 사용자 ID
     * @return 업데이트된 행 수
     */
    @Override
    public int updateSchedule(Long id, String taskTitle, String taskContents, Long userId) {
        LocalDateTime now = LocalDateTime.now();
        return jdbcTemplate.update("""
            UPDATE schedule
            SET taskTitle = ?, taskContents = ?, userId = ?, updated_at = ?
            WHERE id = ?
        """, taskTitle, taskContents, userId, now, id);
    }

    /**
     * 비밀번호 일치 여부 확인
     * @param scheduleId 일정 ID
     * @param password 사용자 입력 비밀번호
     * @return true: 일치, false: 불일치
     */
    public boolean isPasswordMatch(Long scheduleId, String password) {
        String sql = "SELECT COUNT(*) FROM schedule WHERE id = ? AND password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, scheduleId, password);
        return count != null && count > 0;
    }

    /**
     * 일정 삭제
     * @param id 삭제할 일정 ID
     * @return 삭제된 행 수
     */
    @Override
    public int deleteSchedule(Long id) {
        return jdbcTemplate.update("DELETE FROM schedule WHERE id = ?", id);
    }

    /**
     * ScheduleResponseDto로 매핑하는 RowMapper
     * @return RowMapper<ScheduleResponseDto>
     */
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

    /**
     * Schedule 엔티티로 매핑하는 RowMapper
     * @return RowMapper<Schedule>
     */
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

    /**
     * Timestamp → LocalDate 변환 메서드
     * @param timestamp java.sql.Timestamp
     * @return java.time.LocalDate 또는 null
     */
    private LocalDate toLocalDate(Timestamp timestamp) {
        return Optional.ofNullable(timestamp)
                .map(Timestamp::toLocalDateTime)
                .map(LocalDateTime::toLocalDate)
                .orElse(null);
    }
}
