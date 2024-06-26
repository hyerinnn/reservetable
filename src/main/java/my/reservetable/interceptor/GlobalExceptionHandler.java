package my.reservetable.interceptor;

import lombok.extern.slf4j.Slf4j;
import my.reservetable.error.ErrorCode;
import my.reservetable.error.ErrorResponse;
import my.reservetable.exception.DuplicateMemberException;
import my.reservetable.exception.NoRegisterWaitingException;
import my.reservetable.exception.NotExistMemberException;
import my.reservetable.exception.NotFoundEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse illegalArgumentException(IllegalArgumentException e){
        //e.printStackTrace();
        log.error("[IllegalArgumentException] ex", e);
        return new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErrorResponse validationException(BindException e){
        log.error("[BindException] ex ", e);
        ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_REQUEST);
        for(FieldError fieldError : e.getFieldErrors()){
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return response;
    }

    @ExceptionHandler(NotFoundEntityException.class)
    public ErrorResponse notFoundEntityException(NotFoundEntityException e){
        log.error("[NotFoundEntityException] ex ", e);
        ErrorResponse response = new ErrorResponse(e.getErrorCode(), e.getMessage());
        return response;
    }

    @ExceptionHandler(NotExistMemberException.class)
    public ErrorResponse notExistMemberException(NotExistMemberException e){
        log.error("[NotExistMemberException] ex ", e);
        ErrorResponse response = new ErrorResponse(e.getErrorCode(), e.getMessage());
        //ErrorResponse response = new ErrorResponse(ErrorCode.NOT_EXIST_MEMBER);
        return response;
    }

    @ExceptionHandler(DuplicateMemberException.class)
    public ErrorResponse duplicateMemberException(DuplicateMemberException e){
        log.error("[DuplicateMemberException] ex ", e);
        ErrorResponse response = new ErrorResponse(e.getErrorCode(), e.getMessage());
        //ErrorResponse response = new ErrorResponse(ErrorCode.DUPLICATE_MEMBER);
        return response;
    }

    @ExceptionHandler(NoRegisterWaitingException.class)
    public ErrorResponse noRegisterWaitingException(NoRegisterWaitingException e){
        log.error("[NoRegisterWaitingException] ex ", e);
        ErrorResponse response = new ErrorResponse(e.getErrorCode(), e.getMessage());
        return response;
    }
}
