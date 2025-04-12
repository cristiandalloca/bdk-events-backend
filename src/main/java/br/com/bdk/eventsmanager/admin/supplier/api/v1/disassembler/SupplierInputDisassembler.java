package br.com.bdk.eventsmanager.admin.supplier.api.v1.disassembler;

import br.com.bdk.eventsmanager.admin.city.domain.model.City;
import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.supplier.api.v1.model.input.SupplierInput;
import br.com.bdk.eventsmanager.admin.supplier.domain.model.Supplier;
import br.com.bdk.eventsmanager.core.api.disassembler.InputDisassembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class SupplierInputDisassembler implements InputDisassembler<SupplierInput, Supplier> {

    @Override
    public Supplier toEntity(@NonNull SupplierInput input) {
        var supplier = new Supplier();
        this.copyToEntity(input, supplier);
        return supplier;
    }

    @Override
    public void copyToEntity(@NonNull SupplierInput input, @NonNull Supplier supplier) {
        supplier.setCompany(new Company(input.getCompanyId()));
        supplier.setName(input.getName());
        supplier.setPhoneNumber(input.getPhoneNumber());
        supplier.setCellPhoneNumber(input.getCellPhoneNumber());
        supplier.setEmail(input.getEmail());
        supplier.setHasEventsPlaces(input.getHasEventsPlaces());
        supplier.setActive(input.getActive());

        final var address = supplier.getAddress();
        address.setCity(new City(input.getCityId()));
        address.setStreet(input.getStreet());
        address.setComplement(input.getComplement());
        address.setNeighborhood(input.getNeighborhood());
        address.setPostalCode(input.getPostalCode());
        address.setStreetNumber(input.getStreetNumber());
    }
}
