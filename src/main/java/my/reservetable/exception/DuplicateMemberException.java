package my.reservetable.exception;

import my.reservetable.error.ErrorCode;

public class DuplicateMemberException extends RuntimeException {

    private final ErrorCode errorCode;

    public DuplicateMemberException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public DuplicateMemberException(String message) {
        super(message);
        this.errorCode = ErrorCode.DUPLICATE_MEMBER;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
