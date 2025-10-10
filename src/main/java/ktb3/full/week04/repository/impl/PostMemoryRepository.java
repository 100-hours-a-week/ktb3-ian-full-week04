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

    private final Map<Long, Post> idToPost = new ConcurrentHashMap<>();

    @Override
    public PageResponse<Post> findAll(PageRequest pageRequest) {
        int start = idToPost.size() - getOffset(pageRequest);
        int end = Math.max(start - pageRequest.getSize() + 1, 1);

        List<Post> content = new ArrayList<>();
        for (long i = start; i >= end; i--) {
            content.add(idToPost.get(i));
        }

        return PageResponse.of(content, pageRequest.getPage(), pageRequest.getSize(), end > 1);
    }

    @Override
    public Optional<Post> findById(Long postId) {
        Post post = idToPost.get(postId);
        return Post.validateExists(post);
    }

    @Override
    public void save(Post post) {
        long postId = postIdCounter.getAndIncrement();
        post.save(postId);
        idToPost.put(postId, post);
    }

    @Override
    public void saveAll(Iterable<Post> posts) {
        posts.forEach(this::save);
    }

    @Override
    public void update(Post post) {
        idToPost.put(post.getPostId(), post);
    }

    @Override
    public void delete(Post post) {
        idToPost.remove(post.getPostId());
    }
}
