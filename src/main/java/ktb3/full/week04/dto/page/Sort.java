package ktb3.full.week04.dto.page;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import static ktb3.full.week04.common.Constants.MESSAGE_NOT_NULL_SORT_DIRECTION;
import static ktb3.full.week04.common.Constants.MESSAGE_NOT_NULL_SORT_PROPERTY;

@Value
public class Sort {

    @NotNull(message = MESSAGE_NOT_NULL_SORT_DIRECTION)
    Direction direction;

    @NotBlank(message = MESSAGE_NOT_NULL_SORT_PROPERTY)
    String property;

    public static Sort asc(String property) {
        return new Sort(Direction.ASC, property);
    }

    public static Sort desc(String property) {
        return new Sort(Direction.DESC, property);
    }

    public boolean isDescending() {
        return direction == Direction.DESC;
    }
}
