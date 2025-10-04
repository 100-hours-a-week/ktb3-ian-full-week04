package ktb3.full.week04.exception;

import ktb3.full.week04.exception.base.ForbiddenException;

public class NoPermissionException extends ForbiddenException {

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.NO_PERMISSION;
    }
}
