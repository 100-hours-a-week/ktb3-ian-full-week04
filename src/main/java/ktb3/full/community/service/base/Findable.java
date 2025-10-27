package ktb3.full.community.service.base;

import ktb3.full.community.common.exception.base.NotFoundException;

public interface Findable<T, ID> {

    T getOrThrow(ID id) throws NotFoundException;
}
