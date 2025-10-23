package ktb3.full.week04.infrastructure.database.table;

import ktb3.full.week04.domain.User;
import ktb3.full.week04.infrastructure.database.identifier.IdentifierGenerator;
import org.springframework.stereotype.Component;

@Component
public class InMemoryUserTable extends InMemoryAuditingTable<User, Long> {

    public InMemoryUserTable(IdentifierGenerator<User, Long> identifierGenerator) {
        super(identifierGenerator);
    }
}
