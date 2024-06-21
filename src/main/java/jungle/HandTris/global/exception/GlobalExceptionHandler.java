package jungle.HandTris.global.exception;

import jakarta.validation.ConstraintViolationException;
import jungle.HandTris.global.dto.ResponseEnvelope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Map<Class<? extends Exception>, ErrorCode> exceptionMap;

    public GlobalExceptionHandler() {
        // exception을 상속받은 클래스 : 에러코드 짝으로 이루어진 맵을 만든다.
        Map<Class<? extends Exception>, ErrorCode> tempMap = new HashMap<>();
        // 모든 에러코드 반복문
        for (ErrorCode errorCode : ErrorCode.values()) {
            // 에러코드의 exceptions 필드 가져오기
            Set<Class<? extends Exception>> exceptions = errorCode.getExceptions();
            // 모든 exception에 대해서
            for (Class<? extends Exception> exception : exceptions) {
                // exception과 에러코드 페어를 맵에 추가
                tempMap.put(exception, errorCode);
            }
        }
        // 클로벌 예외처리 핸들러 필드에 추가
        this.exceptionMap = Collections.unmodifiableMap(tempMap);
    }

    // 예외 발생시 맵에 있으면 찾아서 반환
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseEnvelope<?>> handleException(Exception e) {
        if (exceptionMap.containsKey(e.getClass())) {
            ErrorCode errorCode = exceptionMap.get(e.getClass());
            Object data = parseErrorData(e);
            ResponseEnvelope<?> res = ResponseEnvelope.of(errorCode.getCode(), data, errorCode.getMessage());
            return ResponseEntity.status(errorCode.getStatus()).body(res);
        }

        // 예상 못한 에러
        log.error("Unexpected error occurred", e);
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(ResponseEnvelope.of(
                        ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                        null,
                        ErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
    }

    private Object parseErrorData(Exception e) {
        return switch (e.getClass().getSimpleName()) {
            case "MethodArgumentNotValidException" ->
                    dataForMethodArgumentNotValidException((MethodArgumentNotValidException) e);
            case "ConstraintViolationException" ->
                    dataForConstraintViolationException((ConstraintViolationException) e);
            default -> null;
        };
    }

    private Object dataForMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return e.getBindingResult().getFieldErrors().stream()
                .map(error -> new FieldError(error.getField(), error.getDefaultMessage()))
                .toList();
    }

    private Object dataForConstraintViolationException(ConstraintViolationException e) {
        return e.getConstraintViolations().stream()
                .map(violation -> new FieldError(violation.getPropertyPath().toString(), violation.getMessage()))
                .toList();
    }

    private record FieldError(String field, String message) {
    }
}
