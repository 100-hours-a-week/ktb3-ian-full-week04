package ktb3.full.week04.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import ktb3.full.week04.common.annotation.constraint.PasswordPattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static ktb3.full.week04.common.Constants.MESSAGE_NOT_NULL_PASSWORD;

@Schema(description = "회원 비밀번호 수정 요청 DTO")
@Getter
@RequiredArgsConstructor
public class UserPasswordUpdateRequest {

    @Schema(description = "비밀번호", example = "Testpassword1!")
    @NotNull(message = MESSAGE_NOT_NULL_PASSWORD)
    @PasswordPattern
    private final String password;
}
