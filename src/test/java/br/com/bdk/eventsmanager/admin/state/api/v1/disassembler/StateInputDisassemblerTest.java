package br.com.bdk.eventsmanager.admin.state.api.v1.disassembler;

import br.com.bdk.eventsmanager.admin.state.api.v1.model.input.StateInput;
import br.com.bdk.eventsmanager.admin.state.domain.model.State;
import br.com.bdk.eventsmanager.admin.state.domain.model.State_;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class StateInputDisassemblerTest {

    @InjectMocks
    private StateInputDisassembler disassembler;

    @Test
    void shouldDisassembleInput() {
        var input = new StateInput();
        String inputName = "NovoNome";
        String inputAcronym = "BBB";
        String inputCountryExternalId = "11125";
        ReflectionTestUtils.setField(input, State_.NAME, inputName);
        ReflectionTestUtils.setField(input, State_.ACRONYM, inputAcronym);
        ReflectionTestUtils.setField(input, "countryId", inputCountryExternalId);

        var result = disassembler.toEntity(input);
        assertThat(result.getName()).isEqualTo(inputName);
        assertThat(result.getAcronym()).isEqualTo(inputAcronym);
        assertThat(result.getCountry().getExternalId()).isEqualTo(inputCountryExternalId);
    }

    @Test
    void shouldCopyToEntity() {
        var input = new StateInput();
        String inputName = "NovoNome";
        String inputAcronym = "BBB";
        String inputCountryExternalId = "11125";
        ReflectionTestUtils.setField(input, State_.NAME, inputName);
        ReflectionTestUtils.setField(input, State_.ACRONYM, inputAcronym);
        ReflectionTestUtils.setField(input, "countryId", inputCountryExternalId);

        State state = new State();
        disassembler.copyToEntity(input, state);
        assertThat(state.getName()).isEqualTo(inputName);
        assertThat(state.getAcronym()).isEqualTo(inputAcronym);
        assertThat(state.getCountry().getExternalId()).isEqualTo(inputCountryExternalId);
    }
}