package ktb3.full.week04.dto.response;


import ktb3.full.week04.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class CommentResponse {

    private final long commentId;
    private final long postId;
    private final long userId;
    private final String author;
    private final String content;
    private final LocalDateTime createdDate;

    public static CommentResponse from(Comment comment) {
        return builder()
                .commentId(comment.getCommentId())
                .postId(comment.getPost().getPostId())
                .userId(comment.getUser().getUserId())
                .author(comment.getUser().getNickname())
                .content(comment.getContent())
                .createdDate(comment.getCreatedAt())
                .build();
    }
}
