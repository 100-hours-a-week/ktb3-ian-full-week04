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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Repository
public class CommentMemoryRepository implements CommentRepository {

    private final AtomicLong commentIdCounter = new AtomicLong(1L);

    private final Map<Long, Comment> idToComment = new ConcurrentHashMap<>();
    private final Map<Long, List<Long>> postIdToLatestComments = new ConcurrentHashMap<>();
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

            idToComment.put(commentId, comment);

            long postId = comment.getPost().getPostId();
            if (!postIdToLatestComments.containsKey(postId)) {
                postIdToLatestComments.put(postId, new ArrayList<>());
                postIdToActiveCommentCounter.put(postId, new AtomicLong());
            }
            postIdToLatestComments.get(postId).add(commentId);
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
        Comment comment = idToComment.get(commentId);
        return Comment.validateExists(comment);
    }

    @Override
    public void update(Comment comment) {
        if (comment.isDeleted()) {
            postIdToActiveCommentCounter.get(comment.getPost().getPostId()).getAndDecrement();
            comment.auditDelete();
        } else {
            comment.auditUpdate();
        }

        idToComment.put(comment.getCommentId(), comment);
    }

    @Override
    public void delete(Comment comment) {
        idToComment.remove(comment.getCommentId());

        try {
            commentLock.lock();
            postIdToLatestComments.get(comment.getPost().getPostId()).remove(comment.getCommentId());
            postIdToActiveCommentCounter.get(comment.getPost().getPostId()).getAndDecrement();
        } finally {
            commentLock.unlock();
        }
    }

    @Override
    public PageResponse<Comment> findAllByLatest(long postId, PageRequest pageRequest) {
        List<Long> ids = postIdToLatestComments.get(postId);

        if (ids == null) {
            return PageResponse.of(new ArrayList<>(), pageRequest, 0);
        }

        int start = ids.size() - getOffset(pageRequest) - 1;

        List<Comment> content = new ArrayList<>();
        int count = 0;
        int curr = start;
        while (count < pageRequest.getSize() && curr >= 0) {
            Comment comment = idToComment.get(ids.get(curr--));

            if (comment.isDeleted()) {
                continue;
            }

            content.add(comment);
            count++;
        }

        return PageResponse.of(content, pageRequest, postIdToActiveCommentCounter.get(postId).get());
    }

    public List<Comment> findAllByPostId(long postId) {
        List<Comment> comments = new ArrayList<>();
        postIdToLatestComments.get(postId).forEach(commentId ->
                comments.add(idToComment.get(commentId)));
        return comments;
    }
}