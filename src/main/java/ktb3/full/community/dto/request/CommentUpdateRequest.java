package ktb3.full.community.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import ktb3.full.community.common.annotation.constraint.CommentContentPattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Schema(title = "댓글 수정 요청 DTO")
@Getter
@RequiredArgsConstructor
public class CommentUpdateRequest {

    @Schema(description = "내용", example = "테스트 댓글입니다.")
    @CommentContentPattern
    private final String content;
}
