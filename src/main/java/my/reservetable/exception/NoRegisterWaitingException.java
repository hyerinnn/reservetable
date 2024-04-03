package my.reservetable.exception;

import my.reservetable.error.ErrorCode;

public class NoRegisterWaitingException extends RuntimeException {

    private final ErrorCode errorCode;

    public NoRegisterWaitingException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public NoRegisterWaitingException(String message) {
        super(message);
        this.errorCode = ErrorCode.INVALID_REQUEST;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
