package ktb3.full.week04.exception;

import ktb3.full.week04.exception.base.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.USER_NOT_FOUND;
    }
}
