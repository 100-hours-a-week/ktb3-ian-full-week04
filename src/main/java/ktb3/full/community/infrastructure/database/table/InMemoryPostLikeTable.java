package ktb3.full.community.infrastructure.database.table;

import ktb3.full.community.domain.PostLike;
import ktb3.full.community.domain.value.CompositeId;
import ktb3.full.community.infrastructure.database.identifier.IdentifierGenerator;
import org.springframework.stereotype.Component;

@Component
public class InMemoryPostLikeTable extends InMemoryTable<PostLike, CompositeId<Long, Long>> {

    public InMemoryPostLikeTable(IdentifierGenerator<PostLike, CompositeId<Long, Long>> identifierGenerator) {
        super(identifierGenerator);
    }
}
