package ktb3.full.week04.service;

import ktb3.full.week04.dto.page.Page;
import ktb3.full.week04.dto.page.Pageable;
import ktb3.full.week04.dto.request.CommentCreateRequest;
import ktb3.full.week04.dto.request.CommentUpdateRequest;
import ktb3.full.week04.dto.response.CommentResponse;

public interface CommentService {

    Page<CommentResponse> getAllComments(Long postId, Pageable pageable);

    void createComment(Long postId, CommentCreateRequest request);

    void updateComment(Long userId, Long commentId, CommentUpdateRequest request);

    void deleteComment(Long userId, Long commentId);
}
