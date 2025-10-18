package ktb3.full.week04.infrastructure.database.table;

import ktb3.full.week04.infrastructure.database.identifier.IdentifierGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class Table<T, ID> {

    private final IdentifierGenerator<T, ID> identifierGenerator;
    private final Map<ID, T> table = new ConcurrentHashMap<>();

    public Collection<T> selectAll() {
        return table.values();
    }

    public T select(ID id) {
        return table.get(id);
    }

    public ID insert(T entity) {
        ID id = identifierGenerator.generate(entity);
        table.put(id, entity);
        return id;
    }

    public void update(ID id, T entity) {
        table.put(id, entity);
    }

    public void delete(ID id) {
        table.remove(id);
    }
}
