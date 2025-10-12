package ktb3.full.week04.domain.base;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
public abstract class Auditing {

    private boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public void auditCreate() {
        this.createdAt = LocalDateTime.now();
        this.auditUpdate();
    }

    public void auditUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void auditDelete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
        this.auditUpdate();
    }

    public static <T extends Auditing> Optional<T> validateExists(T entity) {
        if (entity == null || entity.isDeleted()) {
            return Optional.empty();
        }

        return Optional.of(entity);
    }
}
