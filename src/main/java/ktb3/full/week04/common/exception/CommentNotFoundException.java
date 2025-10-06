package ktb3.full.week04.common.exception;

import ktb3.full.week04.common.exception.base.NotFoundException;

public class CommentNotFoundException extends NotFoundException {

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.COMMENT_NOT_FOUND;
    }
}
