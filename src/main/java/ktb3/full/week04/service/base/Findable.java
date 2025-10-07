package ktb3.full.week04.service.base;

import ktb3.full.week04.common.exception.base.NotFoundException;

public interface Findable<T, ID> {

    T getOrThrow(ID id) throws NotFoundException;
}
