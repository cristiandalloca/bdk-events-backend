package br.com.bdk.eventsmanager.admin.city.domain.validator;

import br.com.bdk.eventsmanager.admin.city.domain.model.City;
import br.com.bdk.eventsmanager.admin.city.domain.model.exception.DuplicateNameCityException;
import br.com.bdk.eventsmanager.admin.city.domain.repository.CityRepository;
import br.com.bdk.eventsmanager.admin.state.domain.model.State;
import br.com.bdk.eventsmanager.admin.state.domain.model.exception.StateNotFoundException;
import br.com.bdk.eventsmanager.admin.state.domain.repository.StateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityRegistrationValidatorTest {

    @InjectMocks
    private CityRegistrationValidator cityRegisterValidator;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private StateRepository stateRepository;

    private City city;

    @BeforeEach
    void setUp() {
        city = new City();
        city.setName("Florianópolis");
        city.setState(new State("123"));
    }

    @Test
    void shouldValidate() {
        when(cityRepository.findByNameIgnoreCase(city.getName())).thenReturn(Optional.of(city));
        when(stateRepository.existsByExternalId(city.getState().getExternalId())).thenReturn(true);
        assertDoesNotThrow(() -> cityRegisterValidator.validate(city));
    }

    @Test
    void shouldThrowExceptionWhenExistingCitySameName() {
        City anotherCity = new City();
        ReflectionTestUtils.setField(anotherCity, "id", 1L);
        when(cityRepository.findByNameIgnoreCase(city.getName())).thenReturn(Optional.of(anotherCity));
        var exception = assertThrows(DuplicateNameCityException.class, () -> cityRegisterValidator.validate(city));
        assertEquals("Já existe uma cidade com nome '%s'".formatted(city.getName()), exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNotExistsStateByStateExternalId() {
        when(cityRepository.findByNameIgnoreCase(city.getName())).thenReturn(Optional.empty());
        when(stateRepository.existsByExternalId(city.getState().getExternalId())).thenReturn(false);
        var exception = assertThrows(StateNotFoundException.class, () -> cityRegisterValidator.validate(city));
        assertEquals("Não foi encontrado um estado com código '%s'".formatted(city.getState().getExternalId()),
                exception.getMessage());
    }
}