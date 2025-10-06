package ktb3.full.week04.dto.response;

import ktb3.full.week04.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserAccountResponse {

    private final String email;
    private final String nickname;
    private final String profileImage;

    public static UserAccountResponse from(User user) {
        return new UserAccountResponse(user.getEmail(), user.getNickname(), user.getProfileImage());
    }
}
