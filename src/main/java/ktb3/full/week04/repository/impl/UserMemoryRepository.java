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
    public void save(User user) {
        long userId = userIdCounter.getAndIncrement();
        user.save(userId);

        idToUser.put(userId, user);
        emailToId.put(user.getEmail(), userId);
        nicknameToId.put(user.getNickname(), userId);
    }

    @Override
    public void saveAll(Iterable<User> users) {
        users.forEach(this::save);
    }

    @Override
    public Optional<User> findById(Long userId) {
        User user = idToUser.get(userId);
        return validateUser(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Long userId = emailToId.get(email);

        if (userId == null) {
            return Optional.empty();
        }

        return validateUser(idToUser.get(userId));
    }

    @Override
    public void update(User user) {
        User existing = idToUser.get(user.getUserId());
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

    private static Optional<User> validateUser(User user) {
        if (user == null || user.isDeleted()) {
            return Optional.empty();
        }

        return Optional.of(user);
    }
}
