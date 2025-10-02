package ktb3.full.week04.exception.base;

import org.springframework.http.HttpStatus;

public abstract class BadRequestException extends CustomException {

    public BadRequestException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
