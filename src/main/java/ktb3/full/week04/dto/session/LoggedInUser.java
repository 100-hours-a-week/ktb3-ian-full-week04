package ktb3.full.week04.dto.session;

import ktb3.full.week04.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LoggedInUser {

    private final long userId;

    public static LoggedInUser from(User user) {
        return new LoggedInUser(user.getUserId());
    }
}
