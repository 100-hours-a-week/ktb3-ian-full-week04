package ktb3.full.week04.repository.impl;

import ktb3.full.week04.domain.User;
import ktb3.full.week04.domain.base.Deletable;
import ktb3.full.week04.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserMemoryRepository implements UserRepository {

    private final AtomicLong userIdCounter = new AtomicLong(1L);

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
        long userId = userIdCounter.getAndIncrement();
        user.save(userId);
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
