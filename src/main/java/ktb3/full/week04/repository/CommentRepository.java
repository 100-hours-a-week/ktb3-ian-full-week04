package ktb3.full.week04.repository;

import ktb3.full.week04.domain.Comment;
import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.dto.page.PageResponse;
import ktb3.full.week04.repository.base.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    PageResponse<Comment> findAll(long postId, PageRequest pageRequest);
}
