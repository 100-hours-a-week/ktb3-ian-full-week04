package ktb3.full.week04.repository;

import ktb3.full.week04.domain.Comment;
import ktb3.full.week04.dto.page.Page;
import ktb3.full.week04.dto.page.Pageable;

public interface CommentRepository {

    Page<Comment> findAll(Long postId, Pageable pageable);

    void save(Comment comment);

    void update(Comment comment);

    void delete(Long commentId);
}
