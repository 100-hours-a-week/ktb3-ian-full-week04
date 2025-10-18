package ktb3.full.week04.common.exception;

import ktb3.full.week04.common.exception.base.BadRequestException;

public class InvalidSortPropertyException extends BadRequestException {

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.INVALID_SORT_PROPERTY;
    }
}
