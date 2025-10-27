package ktb3.full.community.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import ktb3.full.community.common.exception.ApiErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;

@Schema(title = "API 에러 공통 응답")
@Getter
public class ApiErrorResponse {

    @Schema(description = "에러 코드")
    private final String code;

    @Schema(description = "에러 메시지")
    private final String message;

    @Schema(description = "에러 상세 메시지")
    private final String detail;

    @Schema(description = "필드 에러 메시지")
    private final List<FieldError> fieldErrors;

    @Schema(description = "응답 시각")
    private final LocalDateTime timestamp;

    @Schema(description = "요청 경로")
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

    @Schema(title = "API 필드 에러 응답")
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
