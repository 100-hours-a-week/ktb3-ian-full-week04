package ktb3.full.week04.dto.request;

import ktb3.full.week04.annotation.constraint.ValidEmail;
import ktb3.full.week04.annotation.constraint.ValidNickname;
import ktb3.full.week04.annotation.constraint.ValidPassword;
import ktb3.full.week04.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserSignUpRequest {

    @ValidEmail
    private final String email;

    @ValidPassword
    private final String password;

    @ValidNickname
    private final String nickname;

    private final String profileImage;

    public User toEntity() {
        return User.create(email, password, nickname, profileImage);
    }
}
