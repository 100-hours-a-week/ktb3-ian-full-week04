package ktb3.full.week04.exception;

import ktb3.full.week04.exception.base.BadRequestException;

public class InvalidInputException extends BadRequestException {

    public InvalidInputException(String message) {
        super(message);
    }

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.INVALID_INPUT;
    }
}
