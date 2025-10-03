package ktb3.full.week04.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Post extends Auditing {

    private Long postId;
    private final User user;
    private String title;
    private String content;
    private String image;
    private int likeCount;
    private int commentCount;
    private int viewCount;
    private boolean isDeleted;

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
                .isDeleted(false)
                .build();
    }
}
