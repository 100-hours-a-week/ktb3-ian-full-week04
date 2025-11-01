package ktb3.full.community.repository;

import ktb3.full.community.domain.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
