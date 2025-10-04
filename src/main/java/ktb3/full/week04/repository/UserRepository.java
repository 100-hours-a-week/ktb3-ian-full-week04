package ktb3.full.week04.repository;

import ktb3.full.week04.domain.User;
import java.util.Optional;

public interface UserRepository {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    void save(User user);

    Optional<User> findById(Long userId);

    void update(User user);

    void delete(Long userId);
}
