package ktb3.full.week04.infrastructure.database.table;

import ktb3.full.week04.domain.Post;
import ktb3.full.week04.infrastructure.database.identifier.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryPostTable extends InMemoryAuditingTable<Post, Long> {

    private final AtomicLong activePostCounter = new AtomicLong(0L);
    private final PostCommentConnector postCommentConnector;

    public InMemoryPostTable(IdentifierGenerator<Post, Long> identifierGenerator, PostCommentConnector postCommentConnector) {
        super(identifierGenerator);
        this.postCommentConnector = postCommentConnector;
    }

    @Override
    public Long insert(Post entity) {
        Long postId = super.insert(entity);
        activePostCounter.getAndIncrement();
        postCommentConnector.createPost(postId);
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
    public void delete(Long id) {
        super.delete(id);
        activePostCounter.getAndDecrement();
        postCommentConnector.removePost(id);
    }

    public long getTotalActiveElements() {
        return activePostCounter.get();
    }
}
