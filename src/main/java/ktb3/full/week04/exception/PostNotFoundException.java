package ktb3.full.week04.exception;

import ktb3.full.week04.exception.base.NotFoundException;

public class PostNotFoundException extends NotFoundException {

    public PostNotFoundException(String message) {
        super(message);
    }

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.POST_NOT_FOUND;
    }
}
