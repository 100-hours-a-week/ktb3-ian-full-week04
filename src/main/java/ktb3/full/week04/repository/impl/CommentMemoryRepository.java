package ktb3.full.week04.repository.impl;

import ktb3.full.week04.domain.Comment;
import ktb3.full.week04.domain.base.Deletable;
import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.dto.page.PageResponse;
import ktb3.full.week04.dto.page.Sort;
import ktb3.full.week04.infrastructure.database.table.CommentTable;
import ktb3.full.week04.repository.CommentRepository;
import ktb3.full.week04.util.PageUtil;
import ktb3.full.week04.util.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CommentMemoryRepository implements CommentRepository {

    private final CommentTable table;

    @Override
    public Long save(Comment comment) {
        return table.insert(comment);
    }

    @Override
    public void saveAll(Iterable<Comment> comments) {
        comments.forEach(this::save);
    }

    @Override
    public Optional<Comment> findById(Long commentId) {
        return Deletable.validateExists(table.select(commentId));
    }

    @Override
    public void update(Comment comment) {
        table.update(comment.getCommentId(), comment);
    }

    @Override
    public void delete(Comment comment) {
        table.delete(comment.getCommentId(), comment.getPost().getPostId());
    }

    public List<Comment> findAllByPostId(long postId) {
        List<Comment> comments = new ArrayList<>();
        table.selectAll(postId).forEach(comment -> {
            if (!comment.isDeleted()) {
                comments.add(comment);
            }
        });
        return comments;
    }

    @Override
    public PageResponse<Comment> findAll(long postId, PageRequest pageRequest) {
        List<Comment> comments = table.selectAll(postId);

        if (comments == null) {
            return PageResponse.of(new ArrayList<>(), pageRequest, 0);
        }

        comments = comments.stream()
                .sorted(SortUtil.getComparator(Sort.desc("createdAt")))
                .toList();

        return PageResponse.of(PageUtil.paging(comments, pageRequest), pageRequest, table.getTotalActiveElements(postId));
    }
}