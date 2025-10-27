package ktb3.full.community.common.exception;

import ktb3.full.community.common.exception.base.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.USER_NOT_FOUND;
    }
}
