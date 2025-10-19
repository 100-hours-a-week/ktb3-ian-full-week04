package ktb3.full.week04.infrastructure.database.identifier;

import ktb3.full.week04.domain.value.CompositeId;

public interface CompositeIdentifierGenerator<T, FID, SID> extends IdentifierGenerator<T, CompositeId<FID, SID>> {

}
