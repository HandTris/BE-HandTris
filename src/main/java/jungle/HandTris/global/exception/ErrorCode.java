package jungle.HandTris.global.exception;

import jakarta.validation.ConstraintViolationException;
import jungle.HandTris.domain.exception.*;
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

    ALREADY_PLAYING(HttpStatus.BAD_REQUEST, "이미 진행중인 게임입니다.", Set.of(PlayingGameException.class)),
    LIMITED_PARTICIPANT(HttpStatus.BAD_REQUEST, "인원이 꽉 찼습니다.", Set.of(ParticipantLimitedException.class)),
    GAME_ROOM_NOT_FOUND(HttpStatus.BAD_REQUEST, "게임이 존재하지 않습니다.", Set.of(GameRoomNotFoundException.class)),
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "유저가 존재하지 않습니다.", Set.of(MemberNotFoundException.class)),
    MEMBER_RECORD_NOT_FOUND(HttpStatus.BAD_REQUEST, "전적이 존재하지 않습니다.", Set.of(MemberRecordNotFoundException.class)),

    NICKNAME_NOT_CHANGED(HttpStatus.BAD_REQUEST, "현재 닉네임과 동일한 닉네임입니다.", Set.of(NicknameNotChangedException.class)),
    IMAGE_UPLOAD_FAIL(HttpStatus.BAD_REQUEST, "이미지 업로드에 실패하였습니다", Set.of(ImageUploadException.class)),
    FILE_CONVERSION_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "MultipartFile -> File 변환에 실패했습니다", Set.of(FileConversionException.class)),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임 입니다.", Set.of(DuplicateNicknameException.class)),
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 ID 입니다.", Set.of(DuplicateUsernameException.class)),
    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "등록된 사용자가 없습니다.", Set.of(UserNotFoundException.class)),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.", Set.of(PasswordMismatchException.class)),

    UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, "로그인 정보를 다시 확인해 주십시오.", Set.of(UnauthorizedAccessException.class)),
    TOKEN_NOT_PROVIDED(HttpStatus.UNAUTHORIZED, "토큰이 제공되지 않았습니다.", Set.of(TokenNotProvideException.class)),
    INVALID_TOKEN_FORMAT(HttpStatus.UNAUTHORIZED, "잘못된 토큰 형식입니다.", Set.of(InvalidTokenFormatException.class)),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Access 토큰이 만료되었습니다.", Set.of(AccessTokenExpiredException.class)),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Refresh 토큰이 만료되었습니다.", Set.of(RefreshTokenExpiredException.class)),
  
    DISCORD_LOG_APPENDER(HttpStatus.INTERNAL_SERVER_ERROR, "디스코드 로그 전송에 실패하였습니다", Set.of(DiscordLogException.class)),
    DISCORD_CONNECT(HttpStatus.INTERNAL_SERVER_ERROR, "디스코드 연결에 실패하였습니다", Set.of(DiscordException.class));

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
