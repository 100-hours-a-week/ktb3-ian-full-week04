package ktb3.full.community.infrastructure.database.identifier;

import ktb3.full.community.domain.base.Identifiable;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class LongIdentifierGenerator<T extends Identifiable<Long>> implements IdentifierGenerator<T, Long> {

    private final AtomicLong idCounter = new AtomicLong(1L);

    @Override
    public Long generate(T entity) {
        long id = idCounter.getAndIncrement();
        entity.setId(id);
        return id;
    }
}
