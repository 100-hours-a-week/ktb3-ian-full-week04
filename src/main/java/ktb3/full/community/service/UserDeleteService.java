package ktb3.full.community.service;

import ktb3.full.community.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserDeleteService {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    @Transactional
    public void deleteAccount(long userId) {
        // soft delete
        User user = userService.getOrThrow(userId);
        user.delete();
        postService.deleteAllPostByUserId(userId);
        commentService.deleteAllCommentByUserId(userId);
    }
}
