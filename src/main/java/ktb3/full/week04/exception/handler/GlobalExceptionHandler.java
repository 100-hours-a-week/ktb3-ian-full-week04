package ktb3.full.week04.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import ktb3.full.week04.dto.response.ApiErrorCode;
import ktb3.full.week04.dto.response.ApiErrorResponse;
import ktb3.full.week04.exception.base.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiErrorResponse> handleCustomException(HttpServletRequest request, CustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ApiErrorResponse.of(e.getApiErrorCode(), e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest()
                .body(ApiErrorResponse.ofFieldError(ApiErrorCode.INVALID_INPUT, "요청 본문의 값이 올바르지 않습니다.", e.getBindingResult(), request.getRequestURI()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest()
                .body(ApiErrorResponse.ofDetail(ApiErrorCode.INVALID_INPUT, "요청 본문의 형식이 올바르지 않습니다.", e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException e) {
        return ResponseEntity.badRequest()
                .body(ApiErrorResponse.ofDetail(ApiErrorCode.INVALID_INPUT, "필수 쿼리 파라미터가 누락되었습니다.", e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentTypeMismatchException(HttpServletRequest request, MethodArgumentTypeMismatchException e) {
        return ResponseEntity.badRequest()
                .body(ApiErrorResponse.ofDetail(ApiErrorCode.INVALID_INPUT, "요청 파라미터의 타입이 올바르지 않습니다.", e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNoResourceFoundException(HttpServletRequest request, NoResourceFoundException e) {
        return ResponseEntity.badRequest()
                .body(ApiErrorResponse.of(ApiErrorCode.INVALID_INPUT, "요청 URL이 올바르지 않습니다.", request.getRequestURI()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpRequestMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ApiErrorResponse.ofDetail(ApiErrorCode.METHOD_NOT_ALLOWED, "HTTP 요청 메서드가 올바르지 않습니다.", e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMediaTypeNotSupportedException(HttpServletRequest request, HttpMediaTypeNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(ApiErrorResponse.ofDetail(ApiErrorCode.UNSUPPORTED_MEDIA_TYPE, "요청 본문의 Content-Type이 올바르지 않습니다.", e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(HttpServletRequest request, Exception e) {
        log.error("Exception is occurred in path: {}", request.getRequestURI(), e);
        return ResponseEntity.internalServerError()
                .body(ApiErrorResponse.of(ApiErrorCode.INTERNAL_SERVER_ERROR, "현재 서버에서 요청을 처리할 수 없습니다. 잠시 후 다시 시도해주세요.", request.getRequestURI()));
    }
}
