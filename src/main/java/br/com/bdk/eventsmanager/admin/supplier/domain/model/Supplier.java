package br.com.bdk.eventsmanager.admin.supplier.domain.model;

import br.com.bdk.eventsmanager.admin.address.domain.model.Address;
import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.core.entity.ExposedEntity;
import br.com.bdk.eventsmanager.core.validator.PhoneNumber;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "supplier")
@AllArgsConstructor
@NoArgsConstructor
public class Supplier extends ExposedEntity {

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @NotBlank
    @Size(max = 255)
    @Column(name = "name", nullable = false)
    private String name;

    @PhoneNumber
    @Size(max = 11)
    @Column(name = "phone_number", length = 11)
    private String phoneNumber;

    @PhoneNumber
    @Size(max = 11)
    @Column(name = "cell_phone_number", length = 11)
    private String cellPhoneNumber;

    @Embedded
    private Address address = new Address();

    @Email
    @Size(max = 124)
    @Column(name = "email", length = 124)
    private String email;

    @Column(name = "has_events_places", nullable = false)
    private boolean hasEventsPlaces;

    @Column(name = "active", nullable = false)
    private boolean active;

    public Supplier(String externalId) {
        super(externalId);
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
