package ktb3.full.week04.common.exception.base;

import ktb3.full.week04.common.exception.ApiErrorCode;
import org.springframework.http.HttpStatus;

public abstract class CustomException extends RuntimeException {

    public abstract HttpStatus getHttpStatus();
    public abstract ApiErrorCode getApiErrorCode();
}
