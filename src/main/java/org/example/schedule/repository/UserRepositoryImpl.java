package org.example.schedule.repository;

import org.example.schedule.dto.UserResponseDto;
import org.example.schedule.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserResponseDto saveUser(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user").usingGeneratedKeyColumns("id");

        LocalDate now = LocalDate.now();

        Map<String,Object> parameters = new HashMap<>();
        parameters.put("email",user.getEmail());
        parameters.put("name",user.getName());
        parameters.put("created_at", now);
        parameters.put("updated_at", null);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new UserResponseDto(key.longValue(),user.getEmail(),user.getName(),now,null);
    }

    @Override
    public List<UserResponseDto> findAllUsers() {
        {
            return jdbcTemplate.query("select * from schedule.user",userResponseDtoRowMapper());
        }
    }

    @Override
    public Optional<User> findUserById(Long id) {
        List<User> result = jdbcTemplate.query("select * from schedule.user where id = ?", userResponseDtoRowMapperV2(), id);

        return result.stream().findAny();
    }

    @Override
    public int updateUser(Long id, String email, String name) {
        return jdbcTemplate.update("update schedule.user set email = ?,name = ? where id = ?",email,name,id);
    }

    @Override
    public int deleteUser(Long id) {
        return jdbcTemplate.update("delete from schedule.user where id = ?",id);
    }

    private RowMapper<UserResponseDto> userResponseDtoRowMapper(){
        return new RowMapper<UserResponseDto>() {
            @Override
            public UserResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new UserResponseDto(
                        rs.getLong("id"),
                        rs.getString("email"),
                        rs.getString("name"),
                        Optional.ofNullable(rs.getTimestamp("created_at"))
                                .map(Timestamp::toLocalDateTime)
                                .map(LocalDateTime::toLocalDate)
                                .orElse(null),

                        Optional.ofNullable(rs.getTimestamp("updated_at"))
                                .map(Timestamp::toLocalDateTime)
                                .map(LocalDateTime::toLocalDate)
                                .orElse(null)
                );
            }
        };
    }

    private RowMapper<User> userResponseDtoRowMapperV2(){
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(
                        rs.getLong("id"),
                        rs.getString("email"),
                        rs.getString("name"),
                        Optional.ofNullable(rs.getTimestamp("created_at"))
                                .map(Timestamp::toLocalDateTime)
                                .map(LocalDateTime::toLocalDate)
                                .orElse(null),

                        Optional.ofNullable(rs.getTimestamp("updated_at"))
                                .map(Timestamp::toLocalDateTime)
                                .map(LocalDateTime::toLocalDate)
                                .orElse(null)
                );
            }
        };
    }
}
