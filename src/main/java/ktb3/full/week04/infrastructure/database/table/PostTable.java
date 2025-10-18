package ktb3.full.week04.infrastructure.database.table;

import ktb3.full.week04.domain.Post;
import ktb3.full.week04.infrastructure.database.identifier.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class PostTable extends AuditingTable<Post, Long> {

    private final AtomicLong activePostCounter = new AtomicLong(0L);

    public PostTable(IdentifierGenerator<Post, Long> identifierGenerator) {
        super(identifierGenerator);
    }

    @Override
    public Long insert(Post entity) {
        Long postId = super.insert(entity);
        activePostCounter.getAndIncrement();
        return postId;
    }

    @Override
    public void update(Long id, Post entity) {
        super.update(id, entity);
        if (entity.isDeleted()) {
            activePostCounter.getAndDecrement();
        }
    }

    @Override
    public void delete(Long aLong) {
        super.delete(aLong);
        activePostCounter.getAndDecrement();
    }

    public long getTotalActiveElements() {
        return activePostCounter.get();
    }
}
