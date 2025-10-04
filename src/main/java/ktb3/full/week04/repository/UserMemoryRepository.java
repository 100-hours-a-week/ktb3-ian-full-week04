package ktb3.full.week04.repository;

import ktb3.full.week04.domain.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserMemoryRepository implements UserRepository {

    private final AtomicLong userIdCounter = new AtomicLong(1L);

    private final Map<Long, User> users = new HashMap<>();
    private final Map<String, Long> emails = new HashMap<>();
    private final Map<String, Long> nicknames = new HashMap<>();

    @Override
    public boolean existsByEmail(String email) {
        return emails.containsKey(email);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return nicknames.containsKey(nickname);
    }

    @Override
    public long save(User user) {
        long userId = userIdCounter.getAndIncrement();
        users.put(userId, user);
        emails.put(user.getEmail(), userId);
        nicknames.put(user.getNickname(), userId);

        return userId;
    }
}