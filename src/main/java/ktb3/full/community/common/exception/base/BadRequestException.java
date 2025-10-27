package ktb3.full.community.common.exception.base;

import org.springframework.http.HttpStatus;

public abstract class BadRequestException extends CustomException {

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
