package ktb3.full.week04.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private final String message;
    private final T data;

    public static <T> ApiResponse<T> of(String message, T data) {
        return new ApiResponse<>(message, data);
    }

    public static <T> ApiResponse<T> of(String message) {
        return new ApiResponse<>(message, null);
    }
}
