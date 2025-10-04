package ktb3.full.week04.repository;

import ktb3.full.week04.domain.Post;
import ktb3.full.week04.domain.page.Page;
import ktb3.full.week04.domain.page.Pageable;

import java.util.Optional;

public interface PostRepository {

    Page<Post> findAll(Pageable pageable);

    Optional<Post> findById(Long postId);

    void save(Post post);

    void update(Post post);

    void delete(Post post);
}
