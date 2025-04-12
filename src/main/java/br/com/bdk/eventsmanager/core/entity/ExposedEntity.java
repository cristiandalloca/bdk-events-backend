package br.com.bdk.eventsmanager.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@MappedSuperclass
@NoArgsConstructor
public abstract class ExposedEntity extends BaseEntity {

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @PrePersist
    void prePersist() {
        this.externalId = UUID.randomUUID().toString();
    }

    protected ExposedEntity(String externalId) {
        this.externalId = externalId;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
