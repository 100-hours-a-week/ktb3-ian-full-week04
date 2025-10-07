package ktb3.full.week04.dto.page;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode
@Getter
@RequiredArgsConstructor
public class IdAndCreatedDate {
    private final long id;
    private final LocalDateTime createdDate;
}