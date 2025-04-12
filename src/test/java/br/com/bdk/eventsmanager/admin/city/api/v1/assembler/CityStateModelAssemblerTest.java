package br.com.bdk.eventsmanager.admin.city.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.city.domain.model.City;
import br.com.bdk.eventsmanager.admin.state.api.v1.assembler.StateModelAssembler;
import br.com.bdk.eventsmanager.admin.state.api.v1.model.StateModel;
import br.com.bdk.eventsmanager.common.EnvironmentMock;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ValidationAutoConfiguration.class)
@SpringBootTest(classes = CityStateModelAssembler.class)
@ActiveProfiles("test")
class CityStateModelAssemblerTest {

    @Autowired
    private CityStateModelAssembler assembler;

    @MockitoBean
    private StateModelAssembler stateModelAssembler;

    @Test
    void shouldAssemble() {
        final var input = EnvironmentMock.mock(City.class);
        final var stateModel = EnvironmentMock.mock(StateModel.class);
        when(stateModelAssembler.toModel(input.getState())).thenReturn(stateModel);

        final var output = assembler.toModel(input);
        assertEquals(output.getId(), input.getExternalId());
    }

    @Test
    void shouldThrowExceptionWhenSourceIsNull() {
        Assertions.assertThatThrownBy(() -> assembler.toModel(null))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessage("toModel.source: não deve ser nulo");
    }
}