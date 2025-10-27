package ktb3.full.community.common.exception;

import ktb3.full.community.common.exception.base.UnauthorizedException;

public class InvalidCredentialsException extends UnauthorizedException {

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.INVALID_CREDENTIALS;
    }
}
