package ktb3.full.week04.dto.request;

import jakarta.validation.constraints.NotNull;
import ktb3.full.week04.common.annotation.constraint.EmailPattern;
import ktb3.full.week04.common.annotation.constraint.PasswordPattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static ktb3.full.week04.common.Constants.MESSAGE_NOT_NULL_EMAIL;
import static ktb3.full.week04.common.Constants.MESSAGE_NOT_NULL_PASSWORD;

@Getter
@RequiredArgsConstructor
public class UserLoginRequest {

    @NotNull(message = MESSAGE_NOT_NULL_EMAIL)
    @EmailPattern
    private final String email;

    @NotNull(message = MESSAGE_NOT_NULL_PASSWORD)
    @PasswordPattern
    private final String password;
}
