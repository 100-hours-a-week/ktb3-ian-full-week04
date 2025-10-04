package ktb3.full.week04.repository;

import ktb3.full.week04.domain.User;

public interface UserRepository {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    long save(User user);
}
