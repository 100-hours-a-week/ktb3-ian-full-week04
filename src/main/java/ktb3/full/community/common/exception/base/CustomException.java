package ktb3.full.community.common.exception.base;

import ktb3.full.community.common.exception.ApiErrorCode;
import org.springframework.http.HttpStatus;

public abstract class CustomException extends RuntimeException {

    public abstract HttpStatus getHttpStatus();
    public abstract ApiErrorCode getApiErrorCode();
}
