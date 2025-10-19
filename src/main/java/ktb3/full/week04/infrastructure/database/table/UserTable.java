package ktb3.full.week04.infrastructure.database.table;

import ktb3.full.week04.domain.User;
import ktb3.full.week04.infrastructure.database.identifier.IdentifierGenerator;
import org.springframework.stereotype.Component;

@Component
public class UserTable extends AuditingTable<User, Long> {

    public UserTable(IdentifierGenerator<User, Long> identifierGenerator) {
        super(identifierGenerator);
    }
}
