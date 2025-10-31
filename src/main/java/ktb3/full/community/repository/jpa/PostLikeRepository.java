package ktb3.full.community.repository.jpa;

import ktb3.full.community.domain.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    @Query("select pl from PostLike pl where pl.user.id = :userId and pl.post.id = :postId")
    boolean existsAndLiked(@Param("userId") long userID, @Param("postId") long postID);

    Optional<PostLike> findByUserIdAndPostId(long userId, long postId);
}
