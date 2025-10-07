package ktb3.full.week04.dto.page;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PageRequest {

    private final int page;
    private final int size;
}
