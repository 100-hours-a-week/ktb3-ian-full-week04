package ktb3.full.community.common.exception;

import ktb3.full.community.common.exception.base.NotFoundException;

public class CommentNotFoundException extends NotFoundException {

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.COMMENT_NOT_FOUND;
    }
}
