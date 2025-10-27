package ktb3.full.community.domain;

import ktb3.full.community.domain.base.Auditing;
import ktb3.full.community.domain.base.Deletable;
import ktb3.full.community.domain.base.Identifiable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends Auditing implements Deletable, Identifiable<Long> {

    private Long userId;
    private final String email;
    private String password;
    private String nickname;
    private String profileImage;
    private boolean deleted;

    public static User create(String email, String password, String nickname, String profileImage) {
        return User.builder()
                .userId(null)
                .email(email)
                .password(password)
                .nickname(nickname)
                .profileImage(profileImage)
                .deleted(false)
                .build();
    }

    @Override
    public void setId(Long userId) {
        this.userId = userId;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void delete() {
        this.deleted = true;
    }
}
