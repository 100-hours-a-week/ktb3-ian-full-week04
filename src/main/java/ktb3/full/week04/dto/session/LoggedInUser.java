package ktb3.full.week04.dto.session;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Hidden
@Getter
@RequiredArgsConstructor
public class LoggedInUser {

    private final long userId;
}
