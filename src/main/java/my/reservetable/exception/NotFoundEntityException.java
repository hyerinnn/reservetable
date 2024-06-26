package my.reservetable.exception;

import my.reservetable.error.ErrorCode;

public class NotFoundEntityException extends RuntimeException {

    private final ErrorCode errorCode;

    public NotFoundEntityException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public NotFoundEntityException(String message) {
        super(message);
        this.errorCode = ErrorCode.NOT_FOUND_ENTITY;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
