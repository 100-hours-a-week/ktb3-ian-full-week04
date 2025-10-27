package ktb3.full.community.service.impl;

import ktb3.full.community.common.exception.CommentNotFoundException;
import ktb3.full.community.common.exception.base.NotFoundException;
import ktb3.full.community.domain.Comment;
import ktb3.full.community.domain.Post;
import ktb3.full.community.domain.User;
import ktb3.full.community.dto.page.PageRequest;
import ktb3.full.community.dto.page.PageResponse;
import ktb3.full.community.dto.request.CommentCreateRequest;
import ktb3.full.community.dto.request.CommentUpdateRequest;
import ktb3.full.community.dto.response.CommentResponse;
import ktb3.full.community.repository.CommentRepository;
import ktb3.full.community.service.CommentService;
import ktb3.full.community.service.PostService;
import ktb3.full.community.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    private final Lock lock = new ReentrantLock();

    @Override
    public PageResponse<CommentResponse> getAllComments(long postId, PageRequest pageRequest) {
        PageResponse<Comment> commentsPageResponse = commentRepository.findAll(postId, pageRequest);
        List<CommentResponse> responses = commentsPageResponse.getContent().stream().map(CommentResponse::from).toList();

        return PageResponse.to(commentsPageResponse, responses);
    }

    @Override
    public CommentResponse getComment(long commentId) {
        Comment comment = getOrThrow(commentId);
        return CommentResponse.from(comment);
    }

    @Override
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

    @Override
    public CommentResponse updateComment(long userId, long commentId, CommentUpdateRequest request) {
        Comment comment = getOrThrow(commentId);
        userService.validatePermission(userId, comment.getUser().getUserId());

        if (request.getContent() != null) {
            comment.updateContent(request.getContent());
        }

        commentRepository.update(comment);

        return CommentResponse.from(comment);
    }

    @Override
    public void deleteComment(long userId, long commentId) {
        // soft delete
        Comment comment = getOrThrow(commentId);
        userService.validatePermission(userId, comment.getUser().getUserId());
        comment.delete();

        lock.lock();
        try {
            comment.getPost().decreaseCommentCount();
        } finally {
            lock.unlock();
        }

        commentRepository.update(comment);
    }

    @Override
    public Comment getOrThrow(Long commentId) throws NotFoundException {
        return commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
    }
}
