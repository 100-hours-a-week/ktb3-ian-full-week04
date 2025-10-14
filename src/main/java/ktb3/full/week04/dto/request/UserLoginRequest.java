package ktb3.full.week04.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import ktb3.full.week04.common.annotation.constraint.EmailPattern;
import ktb3.full.week04.common.annotation.constraint.PasswordPattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static ktb3.full.week04.common.Constants.MESSAGE_NOT_NULL_EMAIL;
import static ktb3.full.week04.common.Constants.MESSAGE_NOT_NULL_PASSWORD;

@Schema(title = "회원 로그인 요청 DTO")
@Getter
@RequiredArgsConstructor
public class UserLoginRequest {

    @Schema(description = "이메일", example = "test@test.com")
    @NotNull(message = MESSAGE_NOT_NULL_EMAIL)
    @EmailPattern
    private final String email;

    @Schema(description = "비밀번호", example = "Testpassword1!")
    @NotNull(message = MESSAGE_NOT_NULL_PASSWORD)
    @PasswordPattern
    private final String password;
}
