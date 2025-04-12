package br.com.bdk.eventsmanager.admin.service.domain.model;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.core.entity.ExposedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "service")
@AllArgsConstructor
@NoArgsConstructor
public class Service extends ExposedEntity {

    @NotBlank
    @Size(max = 255)
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(columnDefinition = "text", name = "description")
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cost_type", nullable = false)
    private ServiceCostType costType;

    @Column(name = "apply_bdi", nullable = false)
    private boolean applyBDI;

    @Column(name = "apply_seller_commission", nullable = false)
    private boolean applySellerCommission;

    @Column(name = "active")
    private boolean active = true;

    public Service(String externalId) {
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
