package ktb3.full.community.repository.jpa;

import ktb3.full.community.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
