package ktb3.full.week04.exception;

import ktb3.full.week04.exception.base.UnauthorizedException;

public class LoginRequiredException extends UnauthorizedException {

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.LOGIN_REQUIRED;
    }
}
