package ktb3.full.community.repository;

import ktb3.full.community.domain.Comment;
import ktb3.full.community.dto.page.PageRequest;
import ktb3.full.community.dto.page.PageResponse;
import ktb3.full.community.repository.base.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    PageResponse<Comment> findAll(long postId, PageRequest pageRequest);
}
