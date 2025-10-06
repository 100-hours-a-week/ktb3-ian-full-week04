package ktb3.full.week04.common.exception.base;

import org.springframework.http.HttpStatus;

public abstract class InternalServerErrorException extends CustomException {

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
