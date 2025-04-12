package br.com.bdk.eventsmanager.admin.supplier.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.supplier.api.v1.model.SupplierPlaceModel;
import br.com.bdk.eventsmanager.admin.supplier.domain.model.SupplierPlace;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class SupplierPlaceModelAssembler implements ModelAssembler<SupplierPlace, SupplierPlaceModel> {

    @Override
    @NonNull
    public SupplierPlaceModel toModel(@NonNull SupplierPlace supplierPlace) {
        return SupplierPlaceModel.builder()
                .id(supplierPlace.getExternalId())
                .name(supplierPlace.getName())
                .description(supplierPlace.getDescription())
                .maximumCapacityPeople(supplierPlace.getMaximumCapacityPeople())
                .active(supplierPlace.isActive())
                .build();
    }

}
