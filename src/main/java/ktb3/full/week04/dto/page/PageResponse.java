package ktb3.full.week04.dto.page;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class PageResponse<T> {

    private final List<T> content;
    private final int number;
    private final int size;
    private final int numberOfElements;
    private final int totalPages;
    private final long totalElements;

    public static <T> PageResponse<T> of(List<T> content, PageRequest pageRequest, long totalElements) {
        return PageResponse.<T>builder()
                .content(content)
                .number(pageRequest.getNumber())
                .size(pageRequest.getSize())
                .numberOfElements(content.size())
                .totalPages((int) Math.ceilDiv(totalElements, pageRequest.getSize()))
                .totalElements(totalElements)
                .build();
    }

    public static <T, U> PageResponse<T> to(PageResponse<U> pageResponse, List<T> content) {
        return PageResponse.<T>builder()
                .content(content)
                .number(pageResponse.getNumber())
                .size(pageResponse.getSize())
                .numberOfElements(pageResponse.getNumberOfElements())
                .totalPages(pageResponse.getTotalPages())
                .totalElements(pageResponse.getTotalElements())
                .build();
    }
}
