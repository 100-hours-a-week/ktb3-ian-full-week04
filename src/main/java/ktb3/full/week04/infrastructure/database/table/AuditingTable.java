package ktb3.full.week04.infrastructure.database.table;

import ktb3.full.week04.domain.base.Auditing;
import ktb3.full.week04.infrastructure.database.identifier.IdentifierGenerator;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class AuditingTable<T extends Auditing, ID> extends Table<T, ID> {

    public AuditingTable(IdentifierGenerator<T, ID> identifierGenerator) {
        super(identifierGenerator);
    }

    @Override
    public ID insert(T entity) {
        ID id = super.insert(entity);
        entity.auditCreate();
        return id;
    }

    @Override
    public void update(ID id, T entity) {
        super.update(id, entity);
        entity.auditUpdate();
    }
}
