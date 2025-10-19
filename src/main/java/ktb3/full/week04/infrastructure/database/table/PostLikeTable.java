package ktb3.full.week04.infrastructure.database.table;

import ktb3.full.week04.domain.PostLike;
import ktb3.full.week04.domain.value.CompositeId;
import ktb3.full.week04.infrastructure.database.identifier.IdentifierGenerator;
import org.springframework.stereotype.Component;

@Component
public class PostLikeTable extends Table<PostLike, CompositeId<Long, Long>> {

    public PostLikeTable(IdentifierGenerator<PostLike, CompositeId<Long, Long>> identifierGenerator) {
        super(identifierGenerator);
    }
}
