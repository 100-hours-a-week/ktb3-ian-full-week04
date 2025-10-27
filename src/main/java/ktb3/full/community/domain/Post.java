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
public class Post extends Auditing implements Deletable, Identifiable<Long> {

    private Long postId;
    private final User user;
    private String title;
    private String content;
    private String image;
    private int likeCount;
    private int commentCount;
    private int viewCount;
    private boolean deleted;

    public static Post create(User user, String title, String content, String image) {
        return Post.builder()
                .postId(null)
                .user(user)
                .title(title)
                .content(content)
                .image(image)
                .likeCount(0)
                .commentCount(0)
                .viewCount(0)
                .deleted(false)
                .build();
    }

    @Override
    public void setId(Long postId) {
        this.postId = postId;
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
        this.deleted = true;
    }
}
