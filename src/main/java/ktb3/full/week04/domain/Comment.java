package ktb3.full.week04.domain;

import ktb3.full.week04.domain.base.Auditing;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment extends Auditing {

    private Long commentId;
    private final User user;
    private final Post post;
    private String content;
    private boolean isDeleted;

    public static Comment create(User user, Post post, String content) {
        return Comment.builder()
                .commentId(null)
                .user(user)
                .post(post)
                .content(content)
                .isDeleted(false)
                .build();
    }

    public void save(long commentId) {
        this.commentId = commentId;
    }
}
