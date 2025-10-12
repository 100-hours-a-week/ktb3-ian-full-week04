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

@Repository
public class PostMemoryRepository implements PostRepository {

    private final AtomicLong postIdCounter = new AtomicLong(1L);
    private final AtomicLong activePostCounter = new AtomicLong(0L);

    private final Map<Long, Post> idToPost = new ConcurrentHashMap<>();

    @Override
    public PageResponse<Post> findAllByLatest(PageRequest pageRequest) {
        long start = idToPost.size() - getOffset(pageRequest);

        List<Post> content = new ArrayList<>();
        int count = 0;
        long curr = start;
        while (count < pageRequest.getSize() && curr >= 1) {
            Post post = idToPost.get(curr--);

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
        Post post = idToPost.get(postId);
        return Post.validateExists(post);
    }

    @Override
    public Long save(Post post) {
        long postId = postIdCounter.getAndIncrement();
        post.save(postId);

        if (post.getCreatedAt() == null) {
            post.auditCreate();
        }

        idToPost.put(postId, post);
        activePostCounter.getAndIncrement();

        return postId;
    }

    @Override
    public void saveAll(Iterable<Post> posts) {
        posts.forEach(this::save);
    }

    @Override
    public void update(Post post) {
        if (post.isDeleted()) {
            post.auditDelete();
            activePostCounter.getAndDecrement();
        } else {
            post.auditUpdate();
        }

        idToPost.put(post.getPostId(), post);
    }

    @Override
    public void delete(Post post) {
        idToPost.remove(post.getPostId());
        activePostCounter.getAndDecrement();
    }
}
