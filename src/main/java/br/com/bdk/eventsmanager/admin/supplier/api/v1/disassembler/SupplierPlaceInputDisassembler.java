package br.com.bdk.eventsmanager.admin.supplier.api.v1.disassembler;

import br.com.bdk.eventsmanager.admin.supplier.api.v1.model.input.SupplierPlaceInput;
import br.com.bdk.eventsmanager.admin.supplier.domain.model.SupplierPlace;
import br.com.bdk.eventsmanager.core.api.disassembler.InputDisassembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class SupplierPlaceInputDisassembler implements InputDisassembler<SupplierPlaceInput, SupplierPlace> {

    @Override
    @NonNull
    public SupplierPlace toEntity(@NonNull SupplierPlaceInput input) {
        final var suplierPlace = new SupplierPlace();
        this.copyToEntity(input, suplierPlace);
        return suplierPlace;
    }

    @Override
    public void copyToEntity(@NonNull SupplierPlaceInput input, @NonNull SupplierPlace supplierPlace) {
        supplierPlace.setName(input.getName());
        supplierPlace.setDescription(input.getDescription());
        supplierPlace.setMaximumCapacityPeople(input.getMaximumCapacityPeople());
        supplierPlace.setActive(input.getActive());
    }
}
