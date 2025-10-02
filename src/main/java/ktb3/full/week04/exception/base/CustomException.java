package ktb3.full.week04.exception.base;

import ktb3.full.week04.dto.response.ApiErrorCode;
import org.springframework.http.HttpStatus;

public abstract class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }

    public abstract HttpStatus getHttpStatus();
    public abstract ApiErrorCode getApiErrorCode();
}
