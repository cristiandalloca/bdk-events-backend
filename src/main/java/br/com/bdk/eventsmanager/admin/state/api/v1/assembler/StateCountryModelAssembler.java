package br.com.bdk.eventsmanager.admin.state.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.country.api.v1.assembler.CountryModelAssembler;
import br.com.bdk.eventsmanager.admin.state.api.v1.model.StateCountryModel;
import br.com.bdk.eventsmanager.admin.state.domain.model.State;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StateCountryModelAssembler implements ModelAssembler<State, StateCountryModel> {

    private final CountryModelAssembler countryModelAssembler;

    @NonNull
    @Override
    public StateCountryModel toModel(@NonNull State state) {
        return StateCountryModel.builder()
                .id(state.getExternalId())
                .name(state.getName())
                .acronym(state.getAcronym())
                .country(countryModelAssembler.toModel(state.getCountry()))
                .build();
    }
}
