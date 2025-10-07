package ktb3.full.week04.dto.page;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PageResponse<T> {

    private final List<T> content;
    private final int page;
    private final int size;
    private final boolean hasNext;

    public static <T> PageResponse<T> of(List<T> content, int page, int size, boolean hasNext) {
        return new PageResponse<>(content, page, size, hasNext);
    }
}
