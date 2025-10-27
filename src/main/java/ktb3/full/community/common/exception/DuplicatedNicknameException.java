package ktb3.full.community.common.exception;

import ktb3.full.community.common.exception.base.ConflictException;

public class DuplicatedNicknameException extends ConflictException {

    @Override
    public ApiErrorCode getApiErrorCode() {
        return ApiErrorCode.DUPLICATED_NICKNAME;
    }
}
