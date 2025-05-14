package org.example.schedule.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 애플리케이션 전역에서 발생하는 예외를 처리하는 클래스.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @Valid 검증 실패 시 발생하는 예외를 처리합니다.
     *
     * @param e MethodArgumentNotValidException
     * @return 검증 실패 메시지와 함께 400 Bad Request 응답
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.badRequest().body(message);
    }

    /**
     * 잘못된 인자가 들어왔을 때 발생하는 IllegalArgumentException 처리
     *
     * @param e IllegalArgumentException
     * @return 예외 메시지와 함께 400 Bad Request 응답
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegal(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * 처리되지 않은 일반 예외를 처리합니다.
     *
     * @param e Exception
     * @return 서버 오류 메시지와 함께 500 Internal Server Error 응답
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOther(Exception e) {
        return ResponseEntity.internalServerError().body("서버 오류: " + e.getMessage());
    }

    /**
     * 비밀번호 불일치 예외를 처리합니다.
     *
     * @param e PasswordMismatchException
     * @return 403 Forbidden 응답과 예외 메시지
     */
    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<String> handlePasswordError(PasswordMismatchException e) {
        return ResponseEntity.status(403).body(e.getMessage());
    }
}
