package ktb3.full.week04.dto.request;

import jakarta.validation.constraints.NotNull;
import ktb3.full.week04.common.annotation.constraint.CommentContentPattern;
import ktb3.full.week04.domain.Comment;
import ktb3.full.week04.domain.Post;
import ktb3.full.week04.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static ktb3.full.week04.common.Constants.MESSAGE_NOT_NULL_COMMENT_CONTENT;

@Getter
@RequiredArgsConstructor
public class CommentCreateRequest {

    @NotNull(message = MESSAGE_NOT_NULL_COMMENT_CONTENT)
    @CommentContentPattern
    private final String content;

    public Comment toEntity(User user, Post post) {
        return Comment.create(user, post, content);
    }
}
