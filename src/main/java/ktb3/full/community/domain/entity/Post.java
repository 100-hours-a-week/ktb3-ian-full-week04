package ktb3.full.community.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends AuditTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title", nullable = false, length = 26)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    @Column
    private String image;

    @Column(name = "view_count", nullable = false)
    private int viewCount;

    @Column(name = "comment_count", nullable = false)
    private int commentCount;

    @Column(name = "like_count", nullable = false)
    private int likeCount;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    public static Post create(User user, String title, String content, String image) {
        return Post.builder()
                .id(null)
                .user(user)
                .title(title)
                .content(content)
                .image(image)
                .likeCount(0)
                .commentCount(0)
                .viewCount(0)
                .isDeleted(false)
                .build();
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateImage(String image) {
        this.image = image;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }

    public void increaseCommentCount() {
        this.commentCount++;
    }

    public void decreaseCommentCount() {
        this.commentCount--;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void delete() {
        this.isDeleted = true;
        this.auditDeletedAt();
    }

    public void deleteUser() {
        this.user = null;
    }
}
