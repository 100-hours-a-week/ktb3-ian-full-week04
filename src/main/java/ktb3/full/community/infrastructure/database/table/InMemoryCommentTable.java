package ktb3.full.community.infrastructure.database.table;

import ktb3.full.community.domain.Comment;
import ktb3.full.community.infrastructure.database.identifier.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InMemoryCommentTable extends InMemoryAuditingTable<Comment, Long> {

    private final PostCommentRelationManager postCommentRelationManager;

    public InMemoryCommentTable(IdentifierGenerator<Comment, Long> identifierGenerator, PostCommentRelationManager postCommentRelationManager) {
        super(identifierGenerator);
        this.postCommentRelationManager = postCommentRelationManager;
    }

    @Override
    public Long insert(Comment entity) {
        long commentId = super.insert(entity);
        postCommentRelationManager.createComment(entity.getPost().getPostId(), commentId);
        return commentId;
    }

    @Override
    public void update(Long id, Comment entity) {
        super.update(id, entity);
        if (entity.isDeleted()) {
            postCommentRelationManager.decreaseCommentCount(entity.getPost().getPostId());
        }
    }

    public void delete(Long id, long postId) {
        super.delete(id);
        postCommentRelationManager.removeComment(postId, id);
    }

    public List<Comment> selectAll(long postId) {
        return postCommentRelationManager.getCommentIds(postId).stream()
                .map(super::select)
                .toList();
    }

    public long getTotalActiveElements(long postId) {
        return postCommentRelationManager.getTotalActiveComments(postId);
    }
}
