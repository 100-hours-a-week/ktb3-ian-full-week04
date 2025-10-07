package ktb3.full.week04.repository;

import ktb3.full.week04.domain.Comment;
import ktb3.full.week04.repository.base.CrudRepository;
import ktb3.full.week04.repository.base.PagingAndSortingRepository;

public interface CommentRepository extends CrudRepository<Comment, Long>, PagingAndSortingRepository<Comment> {

}
