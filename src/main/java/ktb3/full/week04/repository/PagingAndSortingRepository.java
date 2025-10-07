package ktb3.full.week04.repository;

import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.dto.page.PageResponse;

public interface PagingAndSortingRepository<T> {

    PageResponse<T> findAll(PageRequest pageRequest);
}
