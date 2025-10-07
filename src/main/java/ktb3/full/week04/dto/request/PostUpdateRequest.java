package ktb3.full.week04.dto.request;

import ktb3.full.week04.common.annotation.constraint.PostContentPattern;
import ktb3.full.week04.common.annotation.constraint.PostTitlePattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostUpdateRequest {

    @PostTitlePattern
    private final String title;

    @PostContentPattern
    private final String content;

    private final String image;
}
