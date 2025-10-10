package ktb3.full.week04.dto.response;

import ktb3.full.week04.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserProfileResponse {

    private final long userId;
    private final String nickname;
    private final String profileImage;

    public static UserProfileResponse from(User user) {
        return new UserProfileResponse(user.getUserId(), user.getNickname(), user.getProfileImage());
    }
}
