package ktb3.full.week04.domain.base;

import java.util.Optional;

public interface Deletable {

    boolean isDeleted();

    static <T extends Deletable> Optional<T> validateExists(T entity) {
        if (entity == null || entity.isDeleted()) {
            return Optional.empty();
        }

        return Optional.of(entity);
    }
}
