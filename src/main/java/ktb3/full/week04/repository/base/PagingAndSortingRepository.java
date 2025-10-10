package ktb3.full.week04.repository.base;

import ktb3.full.week04.dto.page.PageRequest;

public interface PagingAndSortingRepository<T> {

    default int getOffset(PageRequest pageRequest) {
        return (pageRequest.getNumber() - 1) * pageRequest.getSize();
    }
}
