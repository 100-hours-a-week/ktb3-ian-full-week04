package ktb3.full.week04.repository.impl;

import ktb3.full.week04.domain.Post;
import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.dto.page.PageResponse;
import ktb3.full.week04.repository.PostRepository;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
    private final List<PostIdAndCreatedDate> latestPosts = new ArrayList<>();

    @Override
    public PageResponse<Post> findAll(PageRequest pageRequest) {
        int pageNumber = pageRequest.getPage();
        int pageSize = pageRequest.getSize();
        int offset =  (pageNumber - 1) * pageSize;
        int start = latestPosts.size() - offset - 1;
        int end = Math.max(start - pageSize + 1, 0);

        List<Post> content = new ArrayList<>();
        for (int i = start; i >= end; i--) {
            PostIdAndCreatedDate pair = latestPosts.get(i);
            content.add(idToPost.get(pair.postId));
        }

        return PageResponse.of(content, pageNumber, pageSize, end > 0);
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
        latestPosts.add(new PostIdAndCreatedDate(postId, post.getCreatedAt()));
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
        // soft delete
        idToPost.put(post.getPostId(), post);
        latestPosts.remove(new PostIdAndCreatedDate(post.getPostId(), post.getCreatedAt()));
    }

    @EqualsAndHashCode
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static class PostIdAndCreatedDate {
        private final long postId;
        private final LocalDateTime createdDate;
    }
}
