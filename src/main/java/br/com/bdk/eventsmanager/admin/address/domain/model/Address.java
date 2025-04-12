package br.com.bdk.eventsmanager.admin.address.domain.model;

import br.com.bdk.eventsmanager.admin.city.domain.model.City;
import br.com.bdk.eventsmanager.core.validator.OnlyNumbers;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {

    @NotBlank
    @OnlyNumbers
    @Size(max = 8)
    @Column(name = "postal_code", length = 8, nullable = false)
    private String postalCode;

    @NotBlank
    @Size(max = 255)
    @Column(name = "street", nullable = false)
    private String street;

    @Size(max = 12)
    @Column(name = "street_number", length = 12)
    private String streetNumber;

    @NotBlank
    @Size(max = 255)
    @Column(name = "neighborhood", nullable = false)
    private String neighborhood;

    @Size(max = 50)
    @Column(name = "complement", length = 50)
    private String complement;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

}
