package ktb3.full.week04.common.exception;

import ktb3.full.week04.common.exception.base.UnauthorizedException;

public class InvalidCredentialsException extends UnauthorizedException {

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.INVALID_CREDENTIALS;
    }
}
