package ktb3.full.community.repository;

import ktb3.full.community.domain.User;
import ktb3.full.community.repository.base.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<User> findByEmail(String email);
}
