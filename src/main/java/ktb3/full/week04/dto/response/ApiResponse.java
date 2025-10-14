package ktb3.full.week04.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    @Schema(description = "메시지", example = "요청이 성공했습니다.")
    private final String message = "요청이 성공했습니다.";
    private final T data;

    private ApiResponse() {
        this.data = null;
    }

    private static final ApiResponse<Void> BASE_RESPONSE = new ApiResponse<>();

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>(data);
    }

    public static ApiResponse<Void> getBaseResponse() {
        return BASE_RESPONSE;
    }
}
