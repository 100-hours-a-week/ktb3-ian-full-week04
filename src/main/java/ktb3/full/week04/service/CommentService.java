package ktb3.full.week04.service;

import ktb3.full.week04.domain.Comment;
import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.dto.page.PageResponse;
import ktb3.full.week04.dto.request.CommentCreateRequest;
import ktb3.full.week04.dto.request.CommentUpdateRequest;
import ktb3.full.week04.dto.response.CommentResponse;
import ktb3.full.week04.service.base.Findable;

public interface CommentService extends Findable<Comment, Long> {

    PageResponse<CommentResponse> getAllComments(Long postId, PageRequest pageRequest);

    void createComment(Long postId, CommentCreateRequest request);

    void updateComment(Long userId, Long commentId, CommentUpdateRequest request);

    void deleteComment(Long userId, Long commentId);
}
