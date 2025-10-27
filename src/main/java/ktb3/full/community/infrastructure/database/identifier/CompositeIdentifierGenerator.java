package ktb3.full.community.infrastructure.database.identifier;

import ktb3.full.community.domain.value.CompositeId;

public interface CompositeIdentifierGenerator<T, FID, SID> extends IdentifierGenerator<T, CompositeId<FID, SID>> {

}
