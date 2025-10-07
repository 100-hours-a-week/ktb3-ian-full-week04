package ktb3.full.week04.dto.page;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PageRequest {

    @Positive
    private final int page;

    @Positive
    private final int size;
}
