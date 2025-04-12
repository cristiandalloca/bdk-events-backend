package br.com.bdk.eventsmanager.admin.company.domain.model;

import br.com.bdk.eventsmanager.admin.address.domain.model.Address;
import br.com.bdk.eventsmanager.core.entity.ExposedEntity;
import br.com.bdk.eventsmanager.core.util.RegexConstants;
import br.com.bdk.eventsmanager.core.validator.Document;
import br.com.bdk.eventsmanager.core.validator.PhoneNumber;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "company")
@AllArgsConstructor
@NoArgsConstructor
public class Company extends ExposedEntity {

    @NotBlank
    @Size(min = 1, max = 255)
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Size(min = 1, max = 255)
    @Column(name = "business_name", nullable = false)
    private String businessName;

    @Embedded
    @Valid
    private Address address = new Address();

    @Email(regexp = RegexConstants.EMAIL_REGEX)
    @Size(max = 124)
    @Column(name = "email", length = 124)
    private String email;

    @PhoneNumber
    @Size(max = 11)
    @Column(name = "phone_number", length = 11)
    private String phoneNumber;

    @NotBlank
    @Document
    @Size(max = 14)
    @Column(name = "document", length = 14, nullable = false)
    private String document;

    @Size(max = 14)
    @Column(name = "state_registration_number", length = 14)
    private String stateRegistrationNumber;

    @Size(max = 11)
    @Column(name = "city_registration_number", length = 11)
    private String cityRegistrationNumber;

    @Column(name = "active", nullable = false)
    private boolean active;

    public Company(String externalId) {
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
