package ktb3.full.week04.exception;

import ktb3.full.week04.dto.response.ApiErrorCode;
import ktb3.full.week04.exception.base.ForbiddenException;

public class NoPermissionException extends ForbiddenException {

    public NoPermissionException(String message) {
        super(message);
    }

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.NO_PERMISSION;
    }
}
