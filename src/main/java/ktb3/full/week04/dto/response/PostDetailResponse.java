package ktb3.full.week04.dto.response;

import ktb3.full.week04.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class PostDetailResponse {

    private final long postId;
    private final String title;
    private final long userId;
    private final String author;
    private final String content;
    private final String image;
    private final boolean liked;
    private final LocalDateTime createdDate;
    private final int likeCount;
    private final int commentCount;
    private final int viewCount;

    public static PostDetailResponse from(Post post, boolean liked) {
        return builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .userId(post.getUser().getUserId())
                .author(post.getUser().getNickname())
                .content(post.getContent())
                .image(post.getImage())
                .liked(liked)
                .createdDate(post.getCreatedAt())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .viewCount(post.getViewCount())
                .build();
    }
}
