package ktb3.full.week04.dto.request;

import jakarta.validation.constraints.NotNull;
import ktb3.full.week04.common.annotation.constraint.EmailPattern;
import ktb3.full.week04.common.annotation.constraint.NicknamePattern;
import ktb3.full.week04.common.annotation.constraint.PasswordPattern;
import ktb3.full.week04.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static ktb3.full.week04.common.Constants.*;

@ToString
@Getter
@RequiredArgsConstructor
public class UserRegisterRequest {

    @NotNull(message = MESSAGE_NOT_NULL_EMAIL)
    @EmailPattern
    private final String email;

    @NotNull(message = MESSAGE_NOT_NULL_PASSWORD)
    @PasswordPattern
    private final String password;

    @NotNull(message = MESSAGE_NOT_NULL_NICKNAME)
    @NicknamePattern
    private final String nickname;

    private final String profileImage;

    public User toEntity() {
        return User.create(email, password, nickname, profileImage);
    }
}
