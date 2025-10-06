package ktb3.full.week04.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends Auditing {

    private Long userId;
    private final String email;
    private String password;
    private String nickname;
    private String profileImage;
    private boolean isDeleted;

    public static User create(String email, String password, String nickname, String profileImage) {
        return User.builder()
                .userId(null)
                .email(email)
                .password(password)
                .nickname(nickname)
                .profileImage(profileImage)
                .isDeleted(false)
                .build();
    }

    public void save(Long userId) {
        this.userId = userId;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
        this.auditUpdate();
    }

    public void updatePassword(String password) {
        this.password = password;
        this.auditUpdate();
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
        this.auditUpdate();
    }

    public void delete() {
        this.isDeleted = true;
        this.auditDelete();
    }
}
