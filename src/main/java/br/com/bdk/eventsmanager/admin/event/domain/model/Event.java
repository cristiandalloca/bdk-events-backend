package br.com.bdk.eventsmanager.admin.event.domain.model;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.eventservice.domain.model.EventService;
import br.com.bdk.eventsmanager.core.entity.ExposedEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "event")
@AllArgsConstructor
@NoArgsConstructor
public class Event extends ExposedEntity {

    @NotBlank
    @Size(max = 255)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Setter(AccessLevel.NONE)
    @ElementCollection(targetClass = EventType.class)
    @CollectionTable(name = "event_type", joinColumns = @JoinColumn(name = "event_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Set<EventType> types = new HashSet<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private Set<EventService> services = new HashSet<>();

    @Column(name = "active")
    private boolean active = true;

    public Event(String externalId) {
        super(externalId);
    }

    public void addType(EventType type) {
        this.getTypes().add(type);
    }

    public void removeAllTypes() {
        this.types = new HashSet<>();
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
