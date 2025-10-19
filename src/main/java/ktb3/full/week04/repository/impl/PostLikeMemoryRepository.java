package ktb3.full.week04.repository.impl;

import ktb3.full.week04.domain.PostLike;
import ktb3.full.week04.domain.value.CompositeId;
import ktb3.full.week04.infrastructure.database.table.PostLikeTable;
import ktb3.full.week04.repository.PostLikeRepository;
import lombok.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PostLikeMemoryRepository implements PostLikeRepository {

    private final PostLikeTable table;

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
