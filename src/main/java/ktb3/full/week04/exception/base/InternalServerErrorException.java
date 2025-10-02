package ktb3.full.week04.exception.base;

import org.springframework.http.HttpStatus;

public abstract class InternalServerErrorException extends CustomException {

    public InternalServerErrorException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
