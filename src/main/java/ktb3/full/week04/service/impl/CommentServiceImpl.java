package ktb3.full.week04.service.impl;

import ktb3.full.week04.common.exception.CommentNotFoundException;
import ktb3.full.week04.common.exception.base.NotFoundException;
import ktb3.full.week04.domain.Comment;
import ktb3.full.week04.domain.Post;
import ktb3.full.week04.domain.User;
import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.dto.page.PageResponse;
import ktb3.full.week04.dto.request.CommentCreateRequest;
import ktb3.full.week04.dto.request.CommentUpdateRequest;
import ktb3.full.week04.dto.response.CommentResponse;
import ktb3.full.week04.repository.CommentRepository;
import ktb3.full.week04.service.CommentService;
import ktb3.full.week04.service.PostService;
import ktb3.full.week04.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    @Override
    public PageResponse<CommentResponse> getAllComments(long postId, PageRequest pageRequest) {
        PageResponse<Comment> commentsPageResponse = commentRepository.findAllByLatest(postId, pageRequest);
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
        post.increaseCommentCount();

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
        comment.getPost().decreaseCommentCount();

        commentRepository.update(comment);
    }

    @Override
    public Comment getOrThrow(Long commentId) throws NotFoundException {
        return commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
    }
}
