package br.com.bdk.eventsmanager.admin.state.api.v1.disassembler;

import br.com.bdk.eventsmanager.admin.country.domain.model.Country;
import br.com.bdk.eventsmanager.admin.state.api.v1.model.input.StateInput;
import br.com.bdk.eventsmanager.admin.state.domain.model.State;
import br.com.bdk.eventsmanager.core.api.disassembler.InputDisassembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class StateInputDisassembler implements InputDisassembler<StateInput, State> {

    @Override
    public State toEntity(@NonNull StateInput input) {
        return new State(input.getName(), input.getAcronym(), new Country(input.getCountryId()));
    }

    @Override
    public void copyToEntity(@NonNull StateInput input, @NonNull State output) {
        output.setName(input.getName());
        output.setAcronym(input.getAcronym());
        output.setCountry(new Country(input.getCountryId()));
    }
}
