package ktb3.full.week04.repository.impl;

import ktb3.full.week04.domain.Post;
import ktb3.full.week04.domain.base.Deletable;
import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.dto.page.PageResponse;
import ktb3.full.week04.dto.page.Sort;
import ktb3.full.week04.infrastructure.database.table.InMemoryPostTable;
import ktb3.full.week04.repository.PostRepository;
import ktb3.full.week04.util.PageUtil;
import ktb3.full.week04.util.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PostMemoryRepository implements PostRepository {

    private final InMemoryPostTable table;

    @Override
    public Optional<Post> findById(Long postId) {
        return Deletable.validateExists(table.select(postId));
    }

    @Override
    public Long save(Post post) {
        return table.insert(post);
    }

    @Override
    public void saveAll(Iterable<Post> posts) {
        posts.forEach(this::save);
    }

    @Override
    public void update(Post post) {
        table.update(post.getPostId(), post);
    }

    @Override
    public void delete(Post post) {
        table.delete(post.getPostId());
    }

    @Override
    public PageResponse<Post> findAll(PageRequest pageRequest, Sort sort) {
        List<Post> sortedList = table.selectAll().stream()
                .sorted(SortUtil.getComparator(sort))
                .toList();

        return PageResponse.of(PageUtil.paging(sortedList, pageRequest), pageRequest, table.getTotalActiveElements());
    }
}
