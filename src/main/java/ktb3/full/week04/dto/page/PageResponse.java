package ktb3.full.week04.dto.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Schema(description = "페이징 응답")
@Getter
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class PageResponse<T> {


    @Schema(description = "응답 데이터")
    private final List<T> content;

    @Schema(description = "요청 페이지 번호", example = "1")
    private final int number;

    @Schema(description = "요청 데이터 수", example = "5")
    private final int size;

    @Schema(description = "응답 데이터 수", example = "5")
    private final int numberOfElements;

    @Schema(description = "전체 페이지 수", example = "20")
    private final int totalPages;

    @Schema(description = "전체 데이터 개수", example = "100")
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
