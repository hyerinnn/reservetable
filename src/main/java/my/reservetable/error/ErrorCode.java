package my.reservetable.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    NOT_FOUND_DATA(HttpStatus.NOT_FOUND, "해당 데이터를 찾을 수 없습니다."),
    NOT_EXIST_MEMBER(HttpStatus.NOT_FOUND, "해당 회원을 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
