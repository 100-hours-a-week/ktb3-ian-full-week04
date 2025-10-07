package ktb3.full.week04.repository;

import ktb3.full.week04.domain.User;
import ktb3.full.week04.repository.base.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<User> findByEmail(String email);
}
