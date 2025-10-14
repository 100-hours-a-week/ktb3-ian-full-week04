package ktb3.full.week04.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Schema(title = "게시글 좋아요 여부 응답 DTO")
@Getter
@RequiredArgsConstructor
public class PostLikeRespnose {

    @Schema(description = "좋아요 여부", example = "true")
    private final boolean liked;
}
