package org.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.schedule.entity.Schedule;

import java.time.LocalDate;

/**
 * 일정 정보를 응답으로 전달할 때 사용하는 DTO 클래스입니다.
 */
@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    /**
     * 일정 ID
     */
    private Long id;

    /**
     * 일정 제목
     */
    private String taskTitle;

    /**
     * 일정 내용
     */
    private String taskContents;

    /**
     * 작성자 이름
     */
    private String userName;

    /**
     * 비밀번호 (조회 응답 시 보이지 않도록 처리 필요)
     */
    private String password;

    /**
     * 작성일
     */
    private LocalDate createdAt;

    /**
     * 수정일
     */
    private LocalDate updatedAt;

    /**
     * Entity 객체와 작성자 이름을 받아서 DTO로 변환하는 생성자입니다.
     *
     * @param schedule Schedule 엔티티
     * @param name     작성자 이름
     */
    public ScheduleResponseDto(Schedule schedule, String name) {
        this.id = schedule.getId();
        this.taskTitle = schedule.getTaskTitle();
        this.taskContents = schedule.getTaskContents();
        this.userName = name;
        this.password = schedule.getPassword(); // 실 운영 시 보안상 제외하는 것이 권장됩니다.
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }

}
