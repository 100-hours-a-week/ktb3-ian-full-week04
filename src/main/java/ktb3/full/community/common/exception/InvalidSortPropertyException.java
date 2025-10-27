package ktb3.full.community.common.exception;

import ktb3.full.community.common.exception.base.BadRequestException;

public class InvalidSortPropertyException extends BadRequestException {

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.INVALID_SORT_PROPERTY;
    }
}
