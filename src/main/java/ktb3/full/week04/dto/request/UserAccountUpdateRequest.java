package ktb3.full.week04.dto.request;

import ktb3.full.week04.common.annotation.constraint.NicknamePattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserAccountUpdateRequest {

    @NicknamePattern
    private final String nickname;

    private final String profileImage;
}
