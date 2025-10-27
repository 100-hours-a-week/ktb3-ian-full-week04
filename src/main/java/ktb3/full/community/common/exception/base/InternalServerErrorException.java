package ktb3.full.community.common.exception.base;

import org.springframework.http.HttpStatus;

public abstract class InternalServerErrorException extends CustomException {

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
