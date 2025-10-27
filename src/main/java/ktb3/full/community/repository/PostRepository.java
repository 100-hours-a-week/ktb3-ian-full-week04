package ktb3.full.community.repository;

import ktb3.full.community.domain.Post;
import ktb3.full.community.dto.page.PageRequest;
import ktb3.full.community.dto.page.PageResponse;
import ktb3.full.community.dto.page.Sort;
import ktb3.full.community.repository.base.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {

    PageResponse<Post> findAll(PageRequest pageRequest, Sort sort);
}
