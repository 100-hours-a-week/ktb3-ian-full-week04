package ktb3.full.week04.repository.impl;

import ktb3.full.week04.domain.Comment;
import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.dto.page.PageResponse;
import ktb3.full.week04.repository.CommentRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CommentMemoryRepository implements CommentRepository {

    private final AtomicLong commentIdCounter = new AtomicLong(1L);

    private final Map<Long, Comment> idToComment = new ConcurrentHashMap<>();
    private final Map<Long, List<Long>> postIdToLatestComments = new ConcurrentHashMap<>();

    @Override
    public void save(Comment comment) {
        long commentId = commentIdCounter.getAndIncrement();
        comment.save(commentId);
        idToComment.put(commentId, comment);

        long postId = comment.getPost().getPostId();
        if (!postIdToLatestComments.containsKey(postId)) {
            postIdToLatestComments.put(postId, new ArrayList<>());
        }
        postIdToLatestComments.get(postId).add(commentId);
    }

    @Override
    public void saveAll(Iterable<Comment> comments) {
        comments.forEach(this::save);
    }

    @Override
    public Optional<Comment> findById(Long commentId) {
        Comment comment = idToComment.get(commentId);
        return Comment.validateExists(comment);
    }

    @Override
    public void update(Comment comment) {
        idToComment.put(comment.getCommentId(), comment);
    }

    @Override
    public void delete(Comment comment) {
        idToComment.remove(comment.getCommentId());
        postIdToLatestComments.get(comment.getPost().getPostId()).remove(comment.getCommentId());
    }

    @Override
    public PageResponse<Comment> findAll(long postId, PageRequest pageRequest) {
        List<Long> ids = postIdToLatestComments.get(postId);
        int start = ids.size() - getOffset(pageRequest) - 1;
        int end = Math.max(start - pageRequest.getSize() + 1, 0);

        List<Comment> content = new ArrayList<>();
        for (int i = start; i >= end; i--) {
            content.add(idToComment.get(ids.get(i)));
        }

        return PageResponse.of(content, pageRequest.getPage(), pageRequest.getSize(), end > 0);
    }
}
