package br.com.bdk.eventsmanager.admin.state.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.country.domain.model.Country;
import br.com.bdk.eventsmanager.admin.state.domain.model.State;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class StateModelAssemblerTest {

    @InjectMocks
    private StateModelAssembler assembler;

    @Test
    void shouldAssembleStateModel() {
        var state = new State("São Paulo", "SP", new Country());
        var model = assembler.toModel(state);
        assertThat(model).isNotNull();
        assertThat(model.getName()).isEqualTo(state.getName());
        assertThat(model.getAcronym()).isEqualTo(state.getAcronym());
    }
}