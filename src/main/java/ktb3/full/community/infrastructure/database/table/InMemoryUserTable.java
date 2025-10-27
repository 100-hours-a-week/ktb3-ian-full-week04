package ktb3.full.community.infrastructure.database.table;

import ktb3.full.community.common.exception.DuplicatedEmailException;
import ktb3.full.community.common.exception.DuplicatedNicknameException;
import ktb3.full.community.domain.User;
import ktb3.full.community.infrastructure.database.identifier.IdentifierGenerator;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class InMemoryUserTable extends InMemoryAuditingTable<User, Long> {

    public InMemoryUserTable(IdentifierGenerator<User, Long> identifierGenerator) {
        super(identifierGenerator);
    }

    private final Lock lock = new ReentrantLock();

    @Override
    public Long insert(User entity) {
        lock.lock();
        try {
            table.values().forEach(user -> {
                if (user.getEmail().equals(entity.getEmail())) {
                    throw new DuplicatedEmailException();
                }
                if (user.getNickname().equals(entity.getNickname())) {
                    throw new DuplicatedNicknameException();
                }
            });
            return super.insert(entity);
        } finally {
            lock.unlock();
        }
    }
}
