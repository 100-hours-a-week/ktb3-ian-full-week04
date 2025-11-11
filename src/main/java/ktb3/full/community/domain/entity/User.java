package ktb3.full.community.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_account")
@Entity
public class User extends AuditTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "nickname", nullable = false, unique = true, columnDefinition = "CHAR(10)")
    private String nickname;

    @Column(name = "profile_image_path", nullable = false, unique = true, length = 255)
    private String profilePath;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    public static User create(String email, String password, String nickname, String profilePath) {
        return User.builder()
                .id(null)
                .email(email)
                .password(password)
                .nickname(nickname)
                .profilePath(profilePath)
                .isDeleted(false)
                .build();
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateProfile(String profilePath) {
        this.profilePath = profilePath;
    }

    public void delete() {
        this.isDeleted = true;
        this.auditDeletedAt();
    }
}
