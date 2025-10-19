package ktb3.full.week04.infrastructure.database.identifier;

import ktb3.full.week04.domain.PostLike;
import ktb3.full.week04.domain.value.CompositeId;
import org.springframework.stereotype.Component;

@Component
public class PostLikeIdentifierGenerator implements CompositeIdentifierGenerator<PostLike, Long, Long> {

    @Override
    public CompositeId<Long, Long> generate(PostLike entity) {
        return new CompositeId<>(entity.getUser().getUserId(), entity.getPost().getPostId());
    }
}
