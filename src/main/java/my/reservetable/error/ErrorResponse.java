package my.reservetable.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final HttpStatus code;
    private final String message;
    private Map<String, String> validation = new HashMap<>();
    public void addValidation(String fieldName, String message){
        this.validation.put(fieldName, message);
    }

    public ErrorResponse(ErrorCode errorCode){
        this(errorCode.getHttpStatus(), errorCode.getMessage());
    }
}
