package br.com.bdk.eventsmanager.admin.city.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.city.api.v1.model.CityStateCountryModel;
import br.com.bdk.eventsmanager.admin.city.domain.model.City;
import br.com.bdk.eventsmanager.admin.state.api.v1.assembler.StateCountryModelAssembler;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CityStateCountryAssembler implements ModelAssembler<City, CityStateCountryModel> {

    private final StateCountryModelAssembler stateCountryModelAssembler;

    @Override
    public CityStateCountryModel toModel(City city) {
        return CityStateCountryModel.builder()
                .id(city.getExternalId())
                .name(city.getName())
                .state(stateCountryModelAssembler.toModel(city.getState()))
                .build();
    }
}
