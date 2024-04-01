package my.reservetable.exception;

import my.reservetable.error.ErrorCode;

public class NotExistMemberException extends RuntimeException {

    private final ErrorCode errorCode;

    public NotExistMemberException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public NotExistMemberException(String message) {
        super(message);
        this.errorCode = ErrorCode.NOT_EXIST_MEMBER;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
