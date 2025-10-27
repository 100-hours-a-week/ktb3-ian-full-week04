package ktb3.full.community.common.exception.base;

import org.springframework.http.HttpStatus;

public abstract class ForbiddenException extends CustomException {

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
