package ktb3.full.week04.repository;

import ktb3.full.week04.domain.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserMemoryRepository implements UserRepository {

    private final Map<Long, User> users = new HashMap<>();
    private final Map<String, Long> emails = new HashMap<>();

    @Override
    public boolean existsByEmail(String email) {
        return emails.containsKey(email);
    }
}