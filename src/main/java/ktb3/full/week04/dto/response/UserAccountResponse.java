package ktb3.full.week04.dto.response;

import ktb3.full.week04.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UserAccountResponse {

    private final long userId;
    private final String email;
    private final String nickname;
    private final String profileImage;
    private final LocalDateTime createdAt;

    public static UserAccountResponse from(User user) {
        return new UserAccountResponse(user.getUserId(), user.getEmail(), user.getNickname(), user.getProfileImage(), user.getCreatedAt());
    }
}
