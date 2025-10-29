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

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    public static User create(String email, String password, String nickname, String profileImage) {
        return User.builder()
                .id(null)
                .email(email)
                .password(password)
                .nickname(nickname)
                .profileImageUrl(profileImage)
                .isDeleted(false)
                .build();
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateProfileImage(String profileImage) {
        this.profileImageUrl = profileImage;
    }

    public void delete() {
        this.isDeleted = true;
        this.auditDeletedAt();
    }
}
