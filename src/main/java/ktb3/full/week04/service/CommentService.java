package ktb3.full.week04.service;

import ktb3.full.week04.domain.Comment;
import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.dto.page.PageResponse;
import ktb3.full.week04.dto.request.CommentCreateRequest;
import ktb3.full.week04.dto.request.CommentUpdateRequest;
import ktb3.full.week04.dto.response.CommentResponse;
import ktb3.full.week04.service.base.Findable;

public interface CommentService extends Findable<Comment, Long> {

    PageResponse<CommentResponse> getAllComments(long postId, PageRequest pageRequest);

    void createComment(long userId, long postId, CommentCreateRequest request);

    void updateComment(long userId, long commentId, CommentUpdateRequest request);

    void deleteComment(long userId, long commentId);
}
