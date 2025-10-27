package ktb3.full.community.dto.page;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Schema(title = "페이징 요청 쿼리 파라미터")
@Getter
@RequiredArgsConstructor
public class PageRequest {

    @Schema(description = "요청 페이지 번호", example = "1")
    @Positive
    private final int number;

    @Schema(description = "요청 데이터 수", example = "5")
    @Positive
    private final int size;
}
