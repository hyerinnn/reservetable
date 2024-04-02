package my.reservetable.interceptor;

import lombok.extern.slf4j.Slf4j;
import my.reservetable.error.ErrorCode;
import my.reservetable.error.ErrorResponse;
import my.reservetable.exception.DuplicateMemberException;
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
public class ControllerExceptionHandler {

/*
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse validationException(IllegalArgumentException e){
        //e.printStackTrace();
        log.error("[exceptionHandler] ex", e);
        return new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }
*/

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErrorResponse validationException(BindException e){
        log.error("[BindException] ex ", e);
        //ErrorResponse response = new ErrorResponse("400", "잘못된 요청입니다.");
        ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_REQUEST);
        for(FieldError fieldError : e.getFieldErrors()){
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return response;
    }

    @ExceptionHandler(NotFoundEntityException.class)
    public ErrorResponse noDataException(NotFoundEntityException e){
        log.error("[NoDataException] ex ", e);
        ErrorResponse response = new ErrorResponse(ErrorCode.NOT_FOUND_DATA);
        return response;
    }

    @ExceptionHandler(NotExistMemberException.class)
    public ErrorResponse notExistMemberException(NotExistMemberException e){
        log.error("[NotExistMemberException] ex ", e);
        //ErrorResponse response = new ErrorResponse(e.getErrorCode(), e.getMessage());
        //ErrorResponse response = new ErrorResponse(e.getErrorCode());
        ErrorResponse response = new ErrorResponse(ErrorCode.NOT_EXIST_MEMBER);
        return response;
    }

    @ExceptionHandler(DuplicateMemberException.class)
    public ErrorResponse duplicateMemberException(DuplicateMemberException e){
        log.error("[DuplicateMemberException] ex ", e);
        ErrorResponse response = new ErrorResponse(ErrorCode.DUPLICATE_MEMBER);
        return response;
    }
}
