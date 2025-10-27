package ktb3.full.community.domain;

import ktb3.full.community.domain.base.Auditing;
import ktb3.full.community.domain.base.Deletable;
import ktb3.full.community.domain.base.Identifiable;
import lombok.*;

@ToString
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment extends Auditing implements Deletable, Identifiable<Long> {

    private Long commentId;
    private final User user;
    private final Post post;
    private String content;
    private boolean deleted;

    public static Comment create(User user, Post post, String content) {
        return Comment.builder()
                .commentId(null)
                .user(user)
                .post(post)
                .content(content)
                .deleted(false)
                .build();
    }

    @Override
    public void setId(Long commentId) {
        this.commentId = commentId;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void delete() {
        this.deleted = true;
    }
}
