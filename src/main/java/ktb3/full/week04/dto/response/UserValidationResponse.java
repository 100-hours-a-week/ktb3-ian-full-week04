package ktb3.full.week04.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Schema(title = "회원 중복 검사 응답 DTO")
@Getter
@RequiredArgsConstructor
public class UserValidationResponse {

    @Schema(description = "사용 가능 여부", example = "true")
    private final boolean available;
}
