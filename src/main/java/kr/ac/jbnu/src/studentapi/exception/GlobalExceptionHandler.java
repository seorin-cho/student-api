
package kr.ac.jbnu.src.studentapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 잘못된 JSON 요청 처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(
            HttpMessageNotReadableException ex) {

        return errorResponse(
                HttpStatus.BAD_REQUEST,
                "올바른 JSON 형식으로 요청해야 합니다."
        );
    }

    // 예상하지 못한 서버 오류 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(
            Exception ex) {

        return errorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "서버 내부 오류가 발생했습니다."
        );
    }

    private ResponseEntity<Map<String, Object>> errorResponse(
            HttpStatus httpStatus, String message) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", "error");
        result.put("data", Map.of("message", message));

        return ResponseEntity.status(httpStatus).body(result);
    }
}
