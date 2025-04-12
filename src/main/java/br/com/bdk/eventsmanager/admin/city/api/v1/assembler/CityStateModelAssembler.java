package br.com.bdk.eventsmanager.admin.city.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.city.api.v1.model.CityStateModel;
import br.com.bdk.eventsmanager.admin.city.domain.model.City;
import br.com.bdk.eventsmanager.admin.state.api.v1.assembler.StateModelAssembler;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CityStateModelAssembler implements ModelAssembler<City, CityStateModel> {

    private final StateModelAssembler stateModelAssembler;

    @Override
    public CityStateModel toModel(City city) {
        return CityStateModel.builder()
                .id(city.getExternalId())
                .name(city.getName())
                .state(stateModelAssembler.toModel(city.getState()))
                .build();
    }

}
