package br.com.bdk.eventsmanager.admin.state.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.state.api.v1.model.StateModel;
import br.com.bdk.eventsmanager.admin.state.domain.model.State;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class StateModelAssembler implements ModelAssembler<State, StateModel> {

    @NonNull
    @Override
    public StateModel toModel(@NonNull State state) {
        return StateModel.builder()
                .id(state.getExternalId())
                .name(state.getName())
                .acronym(state.getAcronym())
                .build();
    }
}
