package ktb3.full.community.infrastructure.database.identifier;

import ktb3.full.community.domain.PostLike;
import ktb3.full.community.domain.value.CompositeId;
import org.springframework.stereotype.Component;

@Component
public class PostLikeIdentifierGenerator implements CompositeIdentifierGenerator<PostLike, Long, Long> {

    @Override
    public CompositeId<Long, Long> generate(PostLike entity) {
        return new CompositeId<>(entity.getUser().getUserId(), entity.getPost().getPostId());
    }
}
