package ktb3.full.week04.dto.request;

import jakarta.validation.constraints.NotNull;
import ktb3.full.week04.common.annotation.constraint.PostContentPattern;
import ktb3.full.week04.common.annotation.constraint.PostTitlePattern;
import ktb3.full.week04.domain.Post;
import ktb3.full.week04.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static ktb3.full.week04.common.Constants.MESSAGE_NOT_NULL_POST_CONTENT;
import static ktb3.full.week04.common.Constants.MESSAGE_NOT_NULL_POST_TITLE;

@Getter
@RequiredArgsConstructor
public class PostCreateRequest {

    @NotNull(message = MESSAGE_NOT_NULL_POST_TITLE)
    @PostTitlePattern
    private final String title;

    @NotNull(message = MESSAGE_NOT_NULL_POST_CONTENT)
    @PostContentPattern
    private final String content;

    private final String image;

    public Post toEntity(User user) {
        return Post.create(user, title, content, image);
    }
}
