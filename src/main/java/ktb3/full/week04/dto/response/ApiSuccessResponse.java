package ktb3.full.week04.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiSuccessResponse<T> {

    @Schema(description = "메시지", example = "요청이 성공했습니다.")
    private final String message = "요청이 성공했습니다.";
    private final T data;

    private ApiSuccessResponse() {
        this.data = null;
    }

    private static final ApiSuccessResponse<Void> BASE_RESPONSE = new ApiSuccessResponse<>();

    public static <T> ApiSuccessResponse<T> of(T data) {
        return new ApiSuccessResponse<>(data);
    }

    public static ApiSuccessResponse<Void> getBaseResponse() {
        return BASE_RESPONSE;
    }
}
