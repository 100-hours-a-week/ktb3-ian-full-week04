package ktb3.full.community.service;

import ktb3.full.community.domain.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostDeleteService {

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;

    @Transactional
    public void deletePost(long userId, long postId) {
        // soft delete
        Post post = postService.getOrThrow(postId);
        userService.validatePermission(userId, post.getUser().getId());
        post.delete();
        commentService.deleteAllCommentByPostId(postId);
    }
}
