package jungle.HandTris.domain.exception;

import jungle.HandTris.global.exception.ErrorCode;

public class CustomException extends RuntimeException {

    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
