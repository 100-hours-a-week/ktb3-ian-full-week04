package ktb3.full.community.service;

import ktb3.full.community.domain.Comment;
import ktb3.full.community.dto.page.PageRequest;
import ktb3.full.community.dto.page.PageResponse;
import ktb3.full.community.dto.request.CommentCreateRequest;
import ktb3.full.community.dto.request.CommentUpdateRequest;
import ktb3.full.community.dto.response.CommentResponse;
import ktb3.full.community.service.base.Findable;

public interface CommentService extends Findable<Comment, Long> {

    PageResponse<CommentResponse> getAllComments(long postId, PageRequest pageRequest);

    CommentResponse getComment(long commentId);

    CommentResponse createComment(long userId, long postId, CommentCreateRequest request);

    CommentResponse updateComment(long userId, long commentId, CommentUpdateRequest request);

    void deleteComment(long userId, long commentId);
}
