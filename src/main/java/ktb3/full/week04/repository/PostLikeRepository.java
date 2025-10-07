package ktb3.full.week04.repository;

import ktb3.full.week04.domain.PostLike;

import java.util.Optional;

public interface PostLikeRepository {

    boolean existsAndLiked(long userId, long postId);

    void saveOrUpdate(PostLike postLike);

    Optional<PostLike> findByUserAndPostId(long userId, long postId);
}
