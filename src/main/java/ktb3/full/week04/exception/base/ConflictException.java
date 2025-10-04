package ktb3.full.week04.exception.base;

import org.springframework.http.HttpStatus;

public abstract class ConflictException extends CustomException {

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
