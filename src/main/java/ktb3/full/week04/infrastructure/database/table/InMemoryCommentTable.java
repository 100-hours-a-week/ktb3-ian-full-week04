package ktb3.full.week04.infrastructure.database.table;

import ktb3.full.week04.domain.Comment;
import ktb3.full.week04.infrastructure.database.identifier.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InMemoryCommentTable extends InMemoryAuditingTable<Comment, Long> {

    private final PostCommentConnector postCommentConnector;

    public InMemoryCommentTable(IdentifierGenerator<Comment, Long> identifierGenerator, PostCommentConnector postCommentConnector) {
        super(identifierGenerator);
        this.postCommentConnector = postCommentConnector;
    }

    @Override
    public Long insert(Comment entity) {
        long commentId = super.insert(entity);
        postCommentConnector.createComment(entity.getPost().getPostId(), commentId);
        return commentId;
    }

    @Override
    public void update(Long id, Comment entity) {
        super.update(id, entity);
        if (entity.isDeleted()) {
            postCommentConnector.decreaseCommentCount(entity.getPost().getPostId());
        }
    }

    public void delete(Long id, long postId) {
        super.delete(id);
        postCommentConnector.removeComment(postId, id);
    }

    public List<Comment> selectAll(long postId) {
        return postCommentConnector.getCommentIds(postId).stream()
                .map(super::select)
                .toList();
    }

    public long getTotalActiveElements(long postId) {
        return postCommentConnector.getTotalActiveComments(postId);
    }
}
