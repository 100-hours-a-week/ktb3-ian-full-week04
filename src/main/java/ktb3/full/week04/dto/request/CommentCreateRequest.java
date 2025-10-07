package ktb3.full.week04.dto.request;

import jakarta.validation.constraints.NotNull;
import ktb3.full.week04.domain.Comment;
import ktb3.full.week04.domain.Post;
import ktb3.full.week04.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentCreateRequest {

    @NotNull
    private final String content;

    public Comment toEntity(User user, Post post) {
        return Comment.create(user, post, content);
    }
}
