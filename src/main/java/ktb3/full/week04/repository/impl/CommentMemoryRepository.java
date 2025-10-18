package ktb3.full.week04.repository.impl;

import ktb3.full.week04.domain.Comment;
import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.dto.page.PageResponse;
import ktb3.full.week04.repository.CommentRepository;
import ktb3.full.week04.util.PageUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Repository
public class CommentMemoryRepository implements CommentRepository {

    private final AtomicLong commentIdCounter = new AtomicLong(1L);

    private final Map<Long, Comment> table = new ConcurrentHashMap<>();
    private final Map<Long, List<Long>> postIdToCommentIds = new ConcurrentHashMap<>();
    private final Map<Long, AtomicLong> postIdToActiveCommentCounter = new ConcurrentHashMap<>();

    private final Lock commentLock = new ReentrantLock();

    @Override
    public Long save(Comment comment) {
        long commentId;

        try {
            commentLock.lock();
            commentId = commentIdCounter.getAndIncrement();
            comment.save(commentId);

            if (comment.getCreatedAt() == null) {
                comment.auditCreate();
            }

            table.put(commentId, comment);

            long postId = comment.getPost().getPostId();
            if (!postIdToCommentIds.containsKey(postId)) {
                postIdToCommentIds.put(postId, new ArrayList<>());
                postIdToActiveCommentCounter.put(postId, new AtomicLong());
            }
            postIdToCommentIds.get(postId).add(commentId);
            postIdToActiveCommentCounter.get(postId).getAndIncrement();
        } finally {
            commentLock.unlock();
        }

        return commentId;
    }

    @Override
    public void saveAll(Iterable<Comment> comments) {
        comments.forEach(this::save);
    }

    @Override
    public Optional<Comment> findById(Long commentId) {
        return validateExists(table.get(commentId));
    }

    @Override
    public void update(Comment comment) {
        if (comment.isDeleted()) {
            postIdToActiveCommentCounter.get(comment.getPost().getPostId()).getAndDecrement();
        }

        comment.auditUpdate();
        table.put(comment.getCommentId(), comment);
    }

    @Override
    public void delete(Comment comment) {
        table.remove(comment.getCommentId());

        try {
            commentLock.lock();
            postIdToCommentIds.get(comment.getPost().getPostId()).remove(comment.getCommentId());
            postIdToActiveCommentCounter.get(comment.getPost().getPostId()).getAndDecrement();
        } finally {
            commentLock.unlock();
        }
    }

    public List<Comment> findAllByPostId(long postId) {
        List<Comment> comments = new ArrayList<>();
        postIdToCommentIds.get(postId).forEach(commentId ->
                comments.add(table.get(commentId)));
        return comments;
    }

    @Override
    public PageResponse<Comment> findAll(long postId, PageRequest pageRequest) {
        List<Long> commentIds = postIdToCommentIds.get(postId);

        if (commentIds == null) {
            return PageResponse.of(new ArrayList<>(), pageRequest, 0);
        }

        commentIds = commentIds.reversed();

        return PageResponse.of(PageUtil.paging(table, commentIds, pageRequest), pageRequest, postIdToActiveCommentCounter.get(postId).get());
    }

    private Optional<Comment> validateExists(Comment comment) {
        if (comment == null || comment.isDeleted()) {
            return Optional.empty();
        }

        return Optional.of(comment);
    }
}