package ktb3.full.week04.repository;

public interface PostLikeRepository {

    boolean check(Long userId, Long postId);

    void saveOrUpdate(Long userId, Long postId);
}
