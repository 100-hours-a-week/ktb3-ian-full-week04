package ktb3.full.week04.dto.response;

import ktb3.full.week04.exception.ApiErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ApiErrorResponse {

    private final String code;
    private final String message;
    private final String detail;
    private final List<FieldError> fieldErrors;
    private final LocalDateTime timestamp;
    private final String path;

    private ApiErrorResponse(ApiErrorCode apiErrorCode, String detail, List<FieldError> fieldErrors, String path) {
        this.code = apiErrorCode.getCode();
        this.message = apiErrorCode.getMessage();
        this.detail = detail;
        this.fieldErrors = fieldErrors;
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }

    public static ApiErrorResponse of(ApiErrorCode apiErrorCode, String path) {
        return new ApiErrorResponse(apiErrorCode, null, null, path);
    }

    public static ApiErrorResponse ofDetail(ApiErrorCode apiErrorCode, String detail, String path) {
        return new ApiErrorResponse(apiErrorCode, detail, null, path);
    }

    public static ApiErrorResponse ofFieldError(ApiErrorCode apiErrorCode, BindingResult bindingResult, String path) {
        return new ApiErrorResponse(apiErrorCode, null, FieldError.ofList(bindingResult), path);
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class FieldError {
        private final String field;
        private final String rejectedValue;
        private final String message;

        private static List<FieldError> ofList(BindingResult bindingResult) {
            List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream().map(fieldError -> new FieldError(
                    fieldError.getField(),
                    fieldError.getRejectedValue() == null ? "" : fieldError.getRejectedValue().toString(),
                    fieldError.getDefaultMessage())).toList();
        }
    }
}
