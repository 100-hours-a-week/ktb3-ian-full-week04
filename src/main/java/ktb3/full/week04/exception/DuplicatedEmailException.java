package ktb3.full.week04.exception;

import ktb3.full.week04.exception.base.ConflictException;

public class DuplicatedEmailException extends ConflictException {

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.DUPLICATED_EMAIL;
    }
}
