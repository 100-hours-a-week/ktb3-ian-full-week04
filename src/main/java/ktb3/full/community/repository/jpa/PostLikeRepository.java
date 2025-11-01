package ktb3.full.community.repository.jpa;

import ktb3.full.community.domain.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    @Query("select pl.isLiked from PostLike pl where pl.user.id = :userId and pl.post.id = :postId")
    Optional<Boolean> existsAndLiked(@Param("userId") long userId, @Param("postId") long postId);

    Optional<PostLike> findByUserIdAndPostId(@Param("userId") long userId, @Param("postId") long postId);
}
