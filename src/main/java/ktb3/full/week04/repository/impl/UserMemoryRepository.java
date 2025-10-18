package ktb3.full.week04.repository.impl;

import ktb3.full.week04.domain.User;
import ktb3.full.week04.domain.base.Deletable;
import ktb3.full.week04.repository.UserRepository;
import ktb3.full.week04.infrastructure.database.identifier.IdentifierGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Repository
public class UserMemoryRepository implements UserRepository {

    private final IdentifierGenerator<User, Long> identifierGenerator;

    private final Map<Long, User> table = new ConcurrentHashMap<>();

    @Override
    public boolean existsByEmail(String email) {
        return table.values().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return table.values().stream()
                .anyMatch(user -> user.getNickname().equals(nickname));
    }

    @Override
    public Long save(User user) {
        long userId = identifierGenerator.generate(user);
        user.auditCreate();
        table.put(userId, user);

        return userId;
    }

    @Override
    public void saveAll(Iterable<User> users) {
        users.forEach(this::save);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return Deletable.validateExists(table.get(userId));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Deletable.validateExists(table.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null));
    }

    @Override
    public void update(User user) {
        user.auditUpdate();
    }

    @Override
    public void delete(User user) {
        table.remove(user.getUserId());
    }

    public List<User> findAll() {
        return new ArrayList<>(table.values());
    }
}
