package jungle.HandTris.global.exception;

import jakarta.validation.ConstraintViolationException;
import jungle.HandTris.domain.exception.DuplicateNicknameException;
import jungle.HandTris.domain.exception.DuplicateUsernameException;
import jungle.HandTris.domain.exception.PasswordMismatchException;
import jungle.HandTris.domain.exception.UserNotFoundException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Set;

@Getter
public enum ErrorCode {
    // Common
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 생겼습니다. 관리자에게 문의하세요.", Set.of()),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "입력 값이 올바르지 않습니다.",
            Set.of(MethodArgumentNotValidException.class, ConstraintViolationException.class)),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메서드입니다.", Set.of(HttpRequestMethodNotSupportedException.class)),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임 입니다.", Set.of(DuplicateNicknameException.class)),
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 ID 입니다.", Set.of(DuplicateUsernameException.class)),
    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "등록된 사용자가 없습니다.", Set.of(UserNotFoundException.class)),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.", Set.of(PasswordMismatchException.class));

    private final HttpStatusCode status;
    private final String code;
    private final String message;
    private final Set<Class<? extends Exception>> exceptions;

    ErrorCode(HttpStatusCode status, String code, String message, Set<Class<? extends Exception>> exceptions) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.exceptions = exceptions;
    }

    ErrorCode(HttpStatusCode status, String message, Set<Class<? extends Exception>> exceptions) {
        this.status = status;
        this.code = String.valueOf(status.value());
        this.message = message;
        this.exceptions = exceptions;
    }
}
