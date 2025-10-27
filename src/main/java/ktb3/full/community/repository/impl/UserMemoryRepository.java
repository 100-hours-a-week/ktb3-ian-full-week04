package ktb3.full.community.repository.impl;

import ktb3.full.community.domain.User;
import ktb3.full.community.domain.base.Deletable;
import ktb3.full.community.infrastructure.database.table.InMemoryUserTable;
import ktb3.full.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserMemoryRepository implements UserRepository {

    private final InMemoryUserTable table;

    @Override
    public boolean existsByEmail(String email) {
        return table.selectAll().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return table.selectAll().stream()
                .anyMatch(user -> user.getNickname().equals(nickname));
    }

    @Override
    public Long save(User user) {
        return table.insert(user);
    }

    @Override
    public void saveAll(Iterable<User> users) {
        users.forEach(this::save);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return Deletable.validateExists(table.select(userId));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Deletable.validateExists(table.selectAll().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null));
    }

    @Override
    public void update(User user) {
        table.update(user.getUserId(), user);
    }

    @Override
    public void delete(User user) {
        table.delete(user.getUserId());
    }

    public List<User> findAll() {
        return new ArrayList<>(table.selectAll());
    }
}
