package ktb3.full.week04.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import ktb3.full.week04.common.annotation.constraint.EmailPattern;
import ktb3.full.week04.common.annotation.constraint.NicknamePattern;
import ktb3.full.week04.common.annotation.constraint.PasswordPattern;
import ktb3.full.week04.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static ktb3.full.week04.common.Constants.*;

@Schema(title = "회원 등록 요청 DTO")
@ToString
@Getter
@RequiredArgsConstructor
public class UserRegisterRequest {

    @Schema(description = "이메일", example = "test@test.com")
    @NotNull(message = MESSAGE_NOT_NULL_EMAIL)
    @EmailPattern
    private final String email;

    @Schema(description = "비밀번호", example = "Testpassword1!")
    @NotNull(message = MESSAGE_NOT_NULL_PASSWORD)
    @PasswordPattern
    private final String password;

    @Schema(description = "닉네임", example = "testNick")
    @NotNull(message = MESSAGE_NOT_NULL_NICKNAME)
    @NicknamePattern
    private final String nickname;

    @Schema(description = "프로필 이미지", example = "https://test.kr/test.jpg")
    private final String profileImage;

    public User toEntity() {
        return User.create(email, password, nickname, profileImage);
    }
}
