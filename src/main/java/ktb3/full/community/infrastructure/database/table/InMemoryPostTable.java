package ktb3.full.community.infrastructure.database.table;

import ktb3.full.community.domain.Post;
import ktb3.full.community.infrastructure.database.identifier.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryPostTable extends InMemoryAuditingTable<Post, Long> {

    private final AtomicLong activePostCounter = new AtomicLong(0L);
    private final PostCommentRelationManager postCommentRelationManager;

    public InMemoryPostTable(IdentifierGenerator<Post, Long> identifierGenerator, PostCommentRelationManager postCommentRelationManager) {
        super(identifierGenerator);
        this.postCommentRelationManager = postCommentRelationManager;
    }

    @Override
    public Long insert(Post entity) {
        Long postId = super.insert(entity);
        activePostCounter.getAndIncrement();
        postCommentRelationManager.createPost(postId);
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
        postCommentRelationManager.removePost(id);
    }

    public long getTotalActiveElements() {
        return activePostCounter.get();
    }
}
