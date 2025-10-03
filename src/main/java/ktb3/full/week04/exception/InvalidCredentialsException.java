package ktb3.full.week04.exception;

import ktb3.full.week04.exception.base.UnauthorizedException;

public class InvalidCredentialsException extends UnauthorizedException {

    public InvalidCredentialsException(String message) {
        super(message);
    }

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.INVALID_CREDENTIALS;
    }
}
