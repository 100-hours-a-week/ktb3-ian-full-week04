package ktb3.full.week04.common.exception.base;

import org.springframework.http.HttpStatus;

public abstract class UnauthorizedException extends CustomException {

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
