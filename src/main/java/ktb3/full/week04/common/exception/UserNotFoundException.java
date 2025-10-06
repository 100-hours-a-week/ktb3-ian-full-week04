package ktb3.full.week04.common.exception;

import ktb3.full.week04.common.exception.base.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.USER_NOT_FOUND;
    }
}
