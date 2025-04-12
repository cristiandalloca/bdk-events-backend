package br.com.bdk.eventsmanager.admin.city.api.v1.disassembler;

import br.com.bdk.eventsmanager.admin.city.api.v1.model.input.CityInput;
import br.com.bdk.eventsmanager.common.EnvironmentMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CityInputDisassemblerTest {

    @InjectMocks
    private CityInputDisassembler disassembler;

    @Test
    void shouldDisassembleToEntity() {
        final var input = EnvironmentMock.mock(CityInput.class);
        final var result = disassembler.toEntity(input);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(input.getName());
        assertThat(result.getState().getExternalId()).isEqualTo(input.getStateId());
    }

}
