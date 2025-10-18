package ktb3.full.week04.repository.impl;

import ktb3.full.week04.domain.Post;
import ktb3.full.week04.domain.base.Deletable;
import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.dto.page.PageResponse;
import ktb3.full.week04.dto.page.Sort;
import ktb3.full.week04.repository.PostRepository;
import ktb3.full.week04.infrastructure.database.identifier.IdentifierGenerator;
import ktb3.full.week04.util.PageUtil;
import ktb3.full.week04.util.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
@Repository
public class PostMemoryRepository implements PostRepository {

    private final IdentifierGenerator<Post, Long> identifierGenerator;

    private final AtomicLong activePostCounter = new AtomicLong(0L);

    private final Map<Long, Post> table = new ConcurrentHashMap<>();
    private final Map<String, List<Long>> ascSortedTable = new ConcurrentHashMap<>();

    private final Lock lock = new ReentrantLock();

    @Override
    public Optional<Post> findById(Long postId) {
        return Deletable.validateExists(table.get(postId));
    }

    @Override
    public Long save(Post post) {
        long postId;

        try {
            lock.lock();
            postId = identifierGenerator.generate(post);
            if (post.getCreatedAt() == null) {
                post.auditCreate();
            }

            table.put(postId, post);
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
        table.put(post.getPostId(), post);
    }

    @Override
    public void delete(Post post) {
        table.remove(post.getPostId());
        activePostCounter.getAndDecrement();
    }

    @Override
    public PageResponse<Post> findAll(PageRequest pageRequest, Sort sort) {
        if (!ascSortedTable.containsKey(sort.getProperty())) {
            ascSortedTable.put(sort.getProperty(), table.values().stream()
                    .sorted(SortUtil.getComparator(sort))
                    .map(Post::getPostId)
                    .toList());
        }

        List<Long> sortedList = ascSortedTable.get(sort.getProperty());
        if (sort.isDescending()) {
            sortedList = sortedList.reversed();
        }

        return PageResponse.of(PageUtil.paging(table, sortedList, pageRequest), pageRequest, activePostCounter.get());
    }
}
