package ktb3.full.week04.repository.impl;

import ktb3.full.week04.domain.Post;
import ktb3.full.week04.domain.base.Deletable;
import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.dto.page.PageResponse;
import ktb3.full.week04.dto.page.Sort;
import ktb3.full.week04.infrastructure.database.table.AuditingTable;
import ktb3.full.week04.repository.PostRepository;
import ktb3.full.week04.util.PageUtil;
import ktb3.full.week04.util.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
@Repository
public class PostMemoryRepository implements PostRepository {

    private final AuditingTable<Post, Long> table;

    private final AtomicLong activePostCounter = new AtomicLong(0L);

    private final Lock lock = new ReentrantLock();

    @Override
    public Optional<Post> findById(Long postId) {
        return Deletable.validateExists(table.select(postId));
    }

    @Override
    public Long save(Post post) {
        long postId;

        try {
            lock.lock();
            postId = table.insert(post);
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
        table.update(post.getPostId(), post);
    }

    @Override
    public void delete(Post post) {
        table.delete(post.getPostId());
        activePostCounter.getAndDecrement();
    }

    @Override
    public PageResponse<Post> findAll(PageRequest pageRequest, Sort sort) {
        List<Long> sortedList = table.selectAll().stream()
                .sorted(SortUtil.getComparator(sort))
                .map(Post::getPostId)
                .toList();

        if (sort.isDescending()) {
            sortedList = sortedList.reversed();
        }

        return PageResponse.of(PageUtil.paging(table, sortedList, pageRequest), pageRequest, activePostCounter.get());
    }
}
