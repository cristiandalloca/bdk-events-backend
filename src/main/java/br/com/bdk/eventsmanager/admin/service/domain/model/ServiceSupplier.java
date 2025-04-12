package br.com.bdk.eventsmanager.admin.service.domain.model;

import br.com.bdk.eventsmanager.admin.supplier.domain.model.Supplier;
import br.com.bdk.eventsmanager.core.entity.ExposedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "service_supplier")
@AllArgsConstructor
@NoArgsConstructor
public class ServiceSupplier extends ExposedEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceSupplier that = (ServiceSupplier) o;
        return getService().getExternalId().equals(that.getService().getExternalId())
                && getSupplier().getExternalId().equals(that.getSupplier().getExternalId());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getService().getExternalId().hashCode();
        result = 31 * result + getSupplier().getExternalId().hashCode();
        return result;
    }
}
