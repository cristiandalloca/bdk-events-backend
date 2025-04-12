package br.com.bdk.eventsmanager.admin.address.api.v1;

import br.com.bdk.eventsmanager.admin.address.api.v1.model.AddressModel;
import br.com.bdk.eventsmanager.admin.address.domain.model.Address;
import br.com.bdk.eventsmanager.admin.city.api.v1.assembler.CityStateModelAssembler;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressModelAssembler implements ModelAssembler<Address, AddressModel> {

    private final CityStateModelAssembler cityStateModelAssembler;

    @Override
    public AddressModel toModel(Address address) {
        return AddressModel.builder()
                .street(address.getStreet())
                .neighborhood(address.getNeighborhood())
                .city(cityStateModelAssembler.toModel(address.getCity()))
                .build();
    }

}
