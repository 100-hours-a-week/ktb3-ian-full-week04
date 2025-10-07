package ktb3.full.week04.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostUpdateRequest {

    private final String title;
    private final String content;
    private final String image;
}
