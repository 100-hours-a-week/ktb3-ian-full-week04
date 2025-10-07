package ktb3.full.week04.repository.base;

import ktb3.full.week04.dto.page.IdAndCreatedDate;
import ktb3.full.week04.dto.page.PageRequest;
import ktb3.full.week04.dto.page.PageResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface PagingAndSortingRepository<T> {

    default PageResponse<T> findAllByLatest(Map<Long, T> idToEntity, List<IdAndCreatedDate> latestEntities, PageRequest pageRequest) {
        int pageNumber = pageRequest.getPage();
        int pageSize = pageRequest.getSize();
        int offset =  (pageNumber - 1) * pageSize;
        int start = latestEntities.size() - offset - 1;
        int end = Math.max(start - pageSize + 1, 0);

        List<T> content = new ArrayList<>();
        for (int i = start; i >= end; i--) {
            IdAndCreatedDate pair = latestEntities.get(i);
            content.add(idToEntity.get(pair.getId()));
        }

        return PageResponse.of(content, pageNumber, pageSize, end > 0);
    }
}
