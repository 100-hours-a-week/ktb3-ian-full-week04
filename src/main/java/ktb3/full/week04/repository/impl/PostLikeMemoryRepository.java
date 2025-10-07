package ktb3.full.week04.repository.impl;

import ktb3.full.week04.domain.PostLike;
import ktb3.full.week04.repository.PostLikeRepository;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PostLikeMemoryRepository implements PostLikeRepository {

    private final Map<UserAndPostId, PostLike> userAndPostIdToPostLike = new ConcurrentHashMap<>();

    @Override
    public boolean existsAndLiked(long userId, long postId) {
        PostLike postLike = userAndPostIdToPostLike.get(new UserAndPostId(userId, postId));

        if (postLike == null) {
            return false;
        }

        return postLike.isLiked();
    }

    @Override
    public void saveOrUpdate(PostLike postLike) {
        UserAndPostId userAndPostId = new UserAndPostId(postLike.getUser().getUserId(), postLike.getPost().getPostId());
        userAndPostIdToPostLike.put(userAndPostId, postLike);
    }

    @Override
    public Optional<PostLike> findByUserAndPostId(long userId, long postId) {
        return Optional.ofNullable(userAndPostIdToPostLike.get(new UserAndPostId(userId, postId)));
    }

    @EqualsAndHashCode
    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static class UserAndPostId {
        private final long userId;
        private final long postId;
    }
}
