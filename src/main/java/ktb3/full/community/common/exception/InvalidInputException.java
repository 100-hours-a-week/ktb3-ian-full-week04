package ktb3.full.community.common.exception;

import ktb3.full.community.common.exception.base.BadRequestException;

public class InvalidInputException extends BadRequestException {

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.INVALID_INPUT;
    }
}
