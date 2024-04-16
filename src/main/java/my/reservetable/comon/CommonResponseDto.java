package my.reservetable.comon;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@RequiredArgsConstructor
public class CommonResponseDto<T> {

    private HttpStatus status;
    private String message;
    private T data;

    public CommonResponseDto(HttpStatus httpStatus, String message, T data) {
        CommonResponseDto.builder()
                .status(httpStatus)
                .message(message)
                .data(data)
                .build();
    }

    public CommonResponseDto(HttpStatus httpStatus, String message) {
        CommonResponseDto.builder()
                .status(httpStatus)
                .message(message)
                .build();
    }

    public CommonResponseDto(HttpStatus httpStatus, T data) {
        CommonResponseDto.builder()
                .status(httpStatus)
                .data(data)
                .build();
    }
}
