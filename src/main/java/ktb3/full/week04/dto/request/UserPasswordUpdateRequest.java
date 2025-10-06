package ktb3.full.week04.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserPasswordUpdateRequest {

    private final String password;
}
