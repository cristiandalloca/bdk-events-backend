package br.com.bdk.eventsmanager.admin.supplier.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.city.api.v1.assembler.CityStateModelAssembler;
import br.com.bdk.eventsmanager.admin.supplier.api.v1.model.SupplierModel;
import br.com.bdk.eventsmanager.admin.supplier.domain.model.Supplier;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SupplierModelAssembler implements ModelAssembler<Supplier, SupplierModel> {

    private final CityStateModelAssembler cityStateModelAssembler;
    private final SupplierCompanyModelAssembler supplierCompanyModelAssembler;

    @NonNull
    @Override
    public SupplierModel toModel(@NonNull Supplier supplier) {
        final var address = supplier.getAddress();
        return SupplierModel.builder()
                .id(supplier.getExternalId())
                .name(supplier.getName())
                .email(supplier.getEmail())
                .phoneNumber(supplier.getPhoneNumber())
                .cellPhoneNumber(supplier.getCellPhoneNumber())
                .postalCode(address.getPostalCode())
                .street(address.getStreet())
                .streetNumber(address.getStreetNumber())
                .neighborhood(address.getNeighborhood())
                .complement(address.getComplement())
                .city(cityStateModelAssembler.toModel(address.getCity()))
                .company(supplierCompanyModelAssembler.toModel(supplier.getCompany()))
                .hasEventsPlaces(supplier.isHasEventsPlaces())
                .active(supplier.isActive())
                .build();
    }
}
