package ktb3.full.week04.dto.request;

import ktb3.full.week04.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserRegisterRequest {

    private final String email;
    private final String password;
    private final String nickname;
    private final String profileImage;

    public User toEntity() {
        return User.create(email, password, nickname, profileImage);
    }
}
