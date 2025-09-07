package ir.ardastudio.model;

import ir.ardastudio.util.IdGenerator;

import java.time.LocalDateTime;

public abstract class Model {
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Model() {}

    public Model(String id, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    protected void onId() {
        this.id = IdGenerator.generate();
    }

    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Model that)) {
            return false;
        }
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
