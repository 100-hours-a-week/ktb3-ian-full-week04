package ktb3.full.week04.dto.page;

import lombok.Value;

@Value
public class Sort {

    Direction direction;
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
