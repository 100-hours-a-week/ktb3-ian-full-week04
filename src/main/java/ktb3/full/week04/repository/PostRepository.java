package ktb3.full.week04.repository;

import ktb3.full.week04.domain.Post;
import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.dto.page.PageResponse;
import ktb3.full.week04.repository.base.CrudRepository;
import ktb3.full.week04.repository.base.PagingAndSortingRepository;

public interface PostRepository extends CrudRepository<Post, Long>, PagingAndSortingRepository<Post> {

    PageResponse<Post> findAll(PageRequest pageRequest);
}
