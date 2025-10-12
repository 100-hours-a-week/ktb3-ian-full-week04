package ktb3.full.week04.repository.impl;

import ktb3.full.week04.domain.User;
import ktb3.full.week04.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserMemoryRepository implements UserRepository {

    private final AtomicLong userIdCounter = new AtomicLong(1L);

    private final Map<Long, User> idToUser = new ConcurrentHashMap<>();
    private final Map<String, Long> emailToId = new ConcurrentHashMap<>();
    private final Map<String, Long> nicknameToId = new ConcurrentHashMap<>();

    @Override
    public boolean existsByEmail(String email) {
        return emailToId.containsKey(email);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return nicknameToId.containsKey(nickname);
    }

    @Override
    public Long save(User user) {
        long userId = userIdCounter.getAndIncrement();
        user.save(userId);

        if (user.getCreatedAt() == null) {
            user.auditCreate();
        }

        idToUser.put(userId, user);
        emailToId.put(user.getEmail(), userId);
        nicknameToId.put(user.getNickname(), userId);

        return userId;
    }

    @Override
    public void saveAll(Iterable<User> users) {
        users.forEach(this::save);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return validateExists(idToUser.get(userId));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Long userId = emailToId.get(email);

        if (userId == null) {
            return Optional.empty();
        }

        return validateExists(idToUser.get(userId));
    }

    @Override
    public void update(User user) {
        User existing = idToUser.get(user.getUserId());
        user.auditUpdate();
        idToUser.put(user.getUserId(), user);

        if (!existing.getNickname().equals(user.getNickname())) {
            nicknameToId.remove(existing.getNickname());
            nicknameToId.put(user.getNickname(), user.getUserId());
        }
    }

    @Override
    public void delete(User user) {
        idToUser.remove(user.getUserId());
        emailToId.remove(user.getEmail());
        nicknameToId.remove(user.getNickname());
    }

    private Optional<User> validateExists(User user) {
        if (user == null || user.isDeleted()) {
            return Optional.empty();
        }

        return Optional.of(user);
    }
}
