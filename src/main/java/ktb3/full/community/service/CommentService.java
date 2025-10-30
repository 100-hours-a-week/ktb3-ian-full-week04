package ktb3.full.community.service;

import ktb3.full.community.common.exception.CommentNotFoundException;
import ktb3.full.community.common.exception.base.NotFoundException;
import ktb3.full.community.domain.entity.Comment;
import ktb3.full.community.domain.entity.Post;
import ktb3.full.community.domain.entity.User;
import ktb3.full.community.dto.page.PageRequest;
import ktb3.full.community.dto.page.PageResponse;
import ktb3.full.community.dto.request.CommentCreateRequest;
import ktb3.full.community.dto.request.CommentUpdateRequest;
import ktb3.full.community.dto.response.CommentResponse;
import ktb3.full.community.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    private final Lock lock = new ReentrantLock();

    public PageResponse<CommentResponse> getAllComments(long postId, PageRequest pageRequest) {
        return null;
    }

    public CommentResponse getComment(long commentId) {
        Comment comment = getOrThrow(commentId);
        return CommentResponse.from(comment);
    }

    public CommentResponse createComment(long userId, long postId, CommentCreateRequest request) {
        User user = userService.getOrThrow(userId);
        Post post = postService.getOrThrow(postId);
        Comment comment = request.toEntity(user, post);

        lock.lock();
        try {
            post.increaseCommentCount();
        } finally {
            lock.unlock();
        }

        commentRepository.save(comment);

        return CommentResponse.from(comment);
    }

    public CommentResponse updateComment(long userId, long commentId, CommentUpdateRequest request) {
        Comment comment = getOrThrow(commentId);
        userService.validatePermission(userId, comment.getUser().getId());

        if (request.getContent() != null) {
            comment.updateContent(request.getContent());
        }

        return CommentResponse.from(comment);
    }

    public void deleteComment(long userId, long commentId) {
        // soft delete
        Comment comment = getOrThrow(commentId);
        userService.validatePermission(userId, comment.getUser().getId());
        comment.delete();

        lock.lock();
        try {
            comment.getPost().decreaseCommentCount();
        } finally {
            lock.unlock();
        }
    }

    public Comment getOrThrow(Long commentId) throws NotFoundException {
        return commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
    }
}
