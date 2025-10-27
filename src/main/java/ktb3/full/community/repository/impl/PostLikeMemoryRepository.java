package ktb3.full.community.repository.impl;

import ktb3.full.community.domain.PostLike;
import ktb3.full.community.domain.value.CompositeId;
import ktb3.full.community.infrastructure.database.table.InMemoryPostLikeTable;
import ktb3.full.community.repository.PostLikeRepository;
import lombok.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PostLikeMemoryRepository implements PostLikeRepository {

    private final InMemoryPostLikeTable table;

    @Override
    public boolean existsAndLiked(long userId, long postId) {
        PostLike postLike = table.select(new CompositeId<>(userId, postId));

        if (postLike == null) {
            return false;
        }

        return postLike.isLiked();
    }

    @Override
    public void saveOrUpdate(PostLike postLike) {
        CompositeId<Long, Long> userAndPostId = new CompositeId<>(postLike.getUser().getUserId(), postLike.getPost().getPostId());
        table.update(userAndPostId, postLike);
    }

    @Override
    public Optional<PostLike> findByUserAndPostId(long userId, long postId) {
        return Optional.ofNullable(table.select(new CompositeId<>(userId, postId)));
    }
}
