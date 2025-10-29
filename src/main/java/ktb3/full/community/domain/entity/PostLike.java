package ktb3.full.community.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(nullable = false)
    private boolean isLiked;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public static PostLike create(User user, Post post) {
        return PostLike.builder()
                .user(user)
                .post(post)
                .isLiked(false)
                .build();
    }

    public void toggle() {
        this.isLiked = !this.isLiked;

        if (this.isLiked) {
            post.increaseLikeCount();
        } else {
            post.decreaseLikeCount();
        }
    }
}
