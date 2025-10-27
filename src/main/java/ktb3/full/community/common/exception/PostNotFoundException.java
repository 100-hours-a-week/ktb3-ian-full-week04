package ktb3.full.community.common.exception;

import ktb3.full.community.common.exception.base.NotFoundException;

public class PostNotFoundException extends NotFoundException {

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.POST_NOT_FOUND;
    }
}
