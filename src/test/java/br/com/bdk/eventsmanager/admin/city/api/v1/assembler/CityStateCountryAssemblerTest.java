package br.com.bdk.eventsmanager.admin.city.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.city.domain.model.City;
import br.com.bdk.eventsmanager.admin.state.api.v1.assembler.StateCountryModelAssembler;
import br.com.bdk.eventsmanager.admin.state.api.v1.model.StateCountryModel;
import br.com.bdk.eventsmanager.common.EnvironmentMock;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ValidationAutoConfiguration.class)
@SpringBootTest(classes = CityStateCountryAssembler.class)
@ActiveProfiles("test")
class CityStateCountryAssemblerTest {

    @Autowired
    private CityStateCountryAssembler assembler;

    @MockitoBean
    private StateCountryModelAssembler stateCountryModelAssembler;

    @Test
    void shouldAssembleCityStateCountry() {
        var city = EnvironmentMock.mock(City.class);
        when(stateCountryModelAssembler.toModel(city.getState())).thenReturn(StateCountryModel.builder().build());

        var result = assembler.toModel(city);
        assertNotNull(result);
        assertAll(
                () -> assertEquals(city.getName(), result.getName()),
                () -> assertEquals(city.getExternalId(), result.getId())
        );
    }

    @Test
    void shouldThrowExceptionWhenSourceIsNull() {
        assertThatThrownBy(() -> assembler.toModel(null))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessage("toModel.source: não deve ser nulo");
    }
}