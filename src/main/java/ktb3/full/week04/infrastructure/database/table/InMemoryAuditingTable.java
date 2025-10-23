package ktb3.full.week04.infrastructure.database.table;

import ktb3.full.week04.domain.base.Auditing;
import ktb3.full.week04.infrastructure.database.identifier.IdentifierGenerator;

public abstract class InMemoryAuditingTable<T extends Auditing, ID> extends InMemoryTable<T, ID> {

    public InMemoryAuditingTable(IdentifierGenerator<T, ID> identifierGenerator) {
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
