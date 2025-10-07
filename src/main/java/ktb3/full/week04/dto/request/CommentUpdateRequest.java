package ktb3.full.week04.dto.request;

import ktb3.full.week04.common.annotation.constraint.CommentContentPattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentUpdateRequest {

    @CommentContentPattern
    private final String content;
}
