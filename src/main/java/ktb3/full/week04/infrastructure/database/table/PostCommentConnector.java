package ktb3.full.week04.infrastructure.database.table;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class PostCommentConnector {

    private final Map<Long, List<Long>> postIdToCommentIds = new ConcurrentHashMap<>();
    private final Map<Long, AtomicLong> postIdToActiveCommentCounter = new ConcurrentHashMap<>();
    private final Lock lock = new ReentrantLock();

    public void createPost(long postId) {
        postIdToCommentIds.put(postId, new ArrayList<>());
        postIdToActiveCommentCounter.put(postId, new AtomicLong());
    }

    public void removePost(long postId) {
        postIdToCommentIds.remove(postId);
        postIdToActiveCommentCounter.remove(postId);
    }

    public void createComment(long postId, long commentId) {
        lock.lock();
        try {
            postIdToCommentIds.get(postId).add(commentId);
        } finally {
            lock.unlock();
        }
        postIdToActiveCommentCounter.get(postId).getAndIncrement();
    }

    public void decreaseCommentCount(long postId) {
        postIdToActiveCommentCounter.get(postId).getAndDecrement();
    }

    public void removeComment(long postId, long commentId) {
        lock.lock();
        try {
            postIdToCommentIds.get(postId).remove(commentId);
        } finally {
            lock.unlock();
        }
        decreaseCommentCount(postId);
    }

    public List<Long> getCommentIds(long postId) {
        return postIdToCommentIds.get(postId);
    }

    public long getTotalActiveComments(long postId) {
        return postIdToActiveCommentCounter.get(postId).get();
    }
}
