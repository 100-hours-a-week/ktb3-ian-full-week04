package ktb3.full.week04.domain.base;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class Auditing {

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    protected Auditing() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void auditUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void auditDelete() {
        this.updatedAt = LocalDateTime.now();
        this.deletedAt = LocalDateTime.now();
    }
}
