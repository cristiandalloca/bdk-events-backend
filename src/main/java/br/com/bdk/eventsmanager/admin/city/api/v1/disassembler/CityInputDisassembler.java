package br.com.bdk.eventsmanager.admin.city.api.v1.disassembler;

import br.com.bdk.eventsmanager.admin.city.api.v1.model.input.CityInput;
import br.com.bdk.eventsmanager.admin.city.domain.model.City;
import br.com.bdk.eventsmanager.admin.state.domain.model.State;
import br.com.bdk.eventsmanager.core.api.disassembler.InputDisassembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CityInputDisassembler implements InputDisassembler<CityInput, City> {

    @Override
    public City toEntity(@NonNull CityInput input) {
        var city = new City();
        this.copyToEntity(input, city);
        return city;
    }

    @Override
    public void copyToEntity(@NonNull CityInput input, @NonNull City city) {
        city.setName(input.getName());
        city.setState(new State(input.getStateId()));
    }
}
