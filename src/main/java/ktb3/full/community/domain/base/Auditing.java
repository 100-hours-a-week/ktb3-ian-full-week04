package ktb3.full.community.domain.base;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class Auditing {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void auditCreate() {
        this.createdAt = LocalDateTime.now();
        this.auditUpdate();
    }

    public void auditUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
