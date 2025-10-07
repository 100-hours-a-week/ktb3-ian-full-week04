package ktb3.full.week04.dto.response;

import ktb3.full.week04.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class PostResponse {

    private final long postId;
    private final String title;
    private final String author;
    private final LocalDateTime createdDate;
    private final int likeCount;
    private final int commentCount;
    private final int viewCount;

    public static PostResponse from(Post post) {
        return builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .author(post.getUser().getNickname())
                .createdDate(post.getCreatedAt())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .viewCount(post.getViewCount())
                .build();
    }
}
