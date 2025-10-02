package ktb3.full.week04.exception.base;

import org.springframework.http.HttpStatus;

public abstract class ForbiddenException extends CustomException {

    public ForbiddenException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
