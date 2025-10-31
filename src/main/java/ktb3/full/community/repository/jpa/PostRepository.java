package ktb3.full.community.repository.jpa;

import ktb3.full.community.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
