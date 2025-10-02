package ktb3.full.week04.exception;

import ktb3.full.week04.dto.response.ApiErrorCode;
import ktb3.full.week04.exception.base.NotFoundException;

public class CommentNotFoundException extends NotFoundException {

    public CommentNotFoundException(String message) {
        super(message);
    }

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.COMMENT_NOT_FOUND;
    }
}
