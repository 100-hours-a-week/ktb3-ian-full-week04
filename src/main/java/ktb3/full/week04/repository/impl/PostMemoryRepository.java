package ktb3.full.week04.repository.impl;

import ktb3.full.week04.domain.Post;
import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.dto.page.PageResponse;
import ktb3.full.week04.repository.PostRepository;
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
public class PostMemoryRepository implements PostRepository {

    private final AtomicLong postIdCounter = new AtomicLong(1L);
    private final AtomicLong activePostCounter = new AtomicLong(0L);

    private final Map<Long, Post> idToPost = new ConcurrentHashMap<>();
    private final List<Long> latest =  new ArrayList<>();

    private final Lock lock = new ReentrantLock();

    @Override
    public PageResponse<Post> findAllByLatest(PageRequest pageRequest) {
        int start = latest.size() - getOffset(pageRequest) - 1;

        List<Post> content = new ArrayList<>();
        int count = 0;
        int curr = start;
        while (count < pageRequest.getSize() && curr >= 0) {
            System.out.println(latest.size() + " " + curr);
            Post post = idToPost.get(latest.get(curr--));

            if (post.isDeleted()) {
                continue;
            }

            content.add(post);
            count++;
        }

        return PageResponse.of(content, pageRequest, activePostCounter.get());
    }

    @Override
    public Optional<Post> findById(Long postId) {
        return validateExists(idToPost.get(postId));
    }

    @Override
    public Long save(Post post) {
        long postId;

        try {
            lock.lock();
            postId = postIdCounter.getAndIncrement();
            post.save(postId);
            if (post.getCreatedAt() == null) {
                post.auditCreate();
            }

            idToPost.put(postId, post);
            latest.add(postId);
            activePostCounter.getAndIncrement();
        } finally {
            lock.unlock();
        }

        return postId;
    }

    @Override
    public void saveAll(Iterable<Post> posts) {
        posts.forEach(this::save);
    }

    @Override
    public void update(Post post) {
        if (post.isDeleted()) {
            activePostCounter.getAndDecrement();
        }

        post.auditUpdate();
        idToPost.put(post.getPostId(), post);
    }

    @Override
    public void delete(Post post) {
        idToPost.remove(post.getPostId());
        activePostCounter.getAndDecrement();

        try {
            lock.lock();
            latest.remove(post.getPostId());
        } finally {
            lock.unlock();
        }
    }

    public List<Post> findAll() {
        return latest.stream().map(idToPost::get).toList();
    }

    private Optional<Post> validateExists(Post post) {
        if (post == null || post.isDeleted()) {
            return Optional.empty();
        }

        return Optional.of(post);
    }
}
