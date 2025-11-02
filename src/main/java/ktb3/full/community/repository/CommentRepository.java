package ktb3.full.community.repository;

import ktb3.full.community.domain.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select c from Comment c left join fetch c.user where c.post.id = :postId order by c.createdAt desc",
            countQuery = "select count(p) from Comment p where p.post.id = :postId")
        Page<Comment> findAllLatestByPostId(@Param("postId") long postId, Pageable pageable);
}
