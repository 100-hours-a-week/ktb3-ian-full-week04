package ktb3.full.week04.dto.request;

import ktb3.full.week04.domain.Post;
import ktb3.full.week04.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostCreateRequest {

    private final String title;
    private final String content;
    private final String image;

    public Post toEntity(User user) {
        return Post.create(user, title, content, image);
    }
}
