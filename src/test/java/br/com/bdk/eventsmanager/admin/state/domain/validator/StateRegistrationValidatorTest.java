package br.com.bdk.eventsmanager.admin.state.domain.validator;

import br.com.bdk.eventsmanager.admin.country.domain.model.Country;
import br.com.bdk.eventsmanager.admin.country.domain.model.exception.CountryNotFoundException;
import br.com.bdk.eventsmanager.admin.country.domain.repository.CountryRepository;
import br.com.bdk.eventsmanager.admin.state.domain.model.State;
import br.com.bdk.eventsmanager.admin.state.domain.model.State_;
import br.com.bdk.eventsmanager.admin.state.domain.model.exception.DuplicateAcronymStateException;
import br.com.bdk.eventsmanager.admin.state.domain.model.exception.DuplicateNameStateException;
import br.com.bdk.eventsmanager.admin.state.domain.repository.StateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StateRegistrationValidatorTest {

    @InjectMocks
    private StateRegistrationValidator validator;

    @Mock
    private StateRepository stateRepository;

    @Mock
    private CountryRepository countryRepository;

    @Test
    void shouldThrowDuplicateNameStateExceptionWhenValidateState() {
        var stateToValidate = new State("São Paulo", "SP", new Country("123"));
        var anotherState = new State();
        ReflectionTestUtils.setField(anotherState, State_.ID, 2L);
        when(stateRepository.findByNameIgnoreCase(stateToValidate.getName()))
                .thenReturn(Optional.of(anotherState));
        var exception = assertThrows(DuplicateNameStateException.class, () -> validator.validate(stateToValidate));
        assertThat(exception.getMessage()).isEqualTo("Já existe um estado com nome '%s'".formatted(stateToValidate.getName()));
    }

    @Test
    void shouldThrowDuplicateAcronymStateExceptionWhenValidateState() {
        var stateToValidate = new State("São Paulo", "SP", new Country("123"));
        var anotherState = new State();
        ReflectionTestUtils.setField(anotherState, State_.ID, 2L);
        when(stateRepository.findByNameIgnoreCase(stateToValidate.getName()))
                .thenReturn(Optional.empty());

        when(stateRepository.findByAcronymIgnoreCase(stateToValidate.getAcronym()))
                .thenReturn(Optional.of(anotherState));
        var exception = assertThrows(DuplicateAcronymStateException.class, () -> validator.validate(stateToValidate));
        assertThat(exception.getMessage()).isEqualTo("Já existe um estado com sigla '%s'".formatted(stateToValidate.getAcronym()));
    }

    @Test
    void shouldThrowCountryNotFoundExceptionWhenValidateState() {
        var stateToValidate = new State("São Paulo", "SP", new Country("123"));
        when(stateRepository.findByNameIgnoreCase(stateToValidate.getName()))
                .thenReturn(Optional.empty());

        when(stateRepository.findByAcronymIgnoreCase(stateToValidate.getAcronym()))
                .thenReturn(Optional.empty());

        String countryExternalId = stateToValidate.getCountry().getExternalId();
        when(countryRepository.existsByExternalId(countryExternalId))
                .thenReturn(false);
        var exception = assertThrows(CountryNotFoundException.class, () -> validator.validate(stateToValidate));
        assertThat(exception.getMessage()).isEqualTo("Não foi encontrado um país com código '%s'".formatted(countryExternalId));
    }

    @Test
    void shouldDoesNotThrowExceptionWhenValidateState1() {
        var stateToValidate = new State("São Paulo", "SP", new Country("123"));
        ReflectionTestUtils.setField(stateToValidate, State_.ID, 2L);

        var anotherState = new State();
        ReflectionTestUtils.setField(anotherState, State_.ID, 2L);
        when(stateRepository.findByNameIgnoreCase(stateToValidate.getName()))
                .thenReturn(Optional.of(anotherState));

        when(stateRepository.findByAcronymIgnoreCase(stateToValidate.getAcronym()))
                .thenReturn(Optional.of(anotherState));

        String countryExternalId = stateToValidate.getCountry().getExternalId();
        when(countryRepository.existsByExternalId(countryExternalId))
                .thenReturn(true);

        assertDoesNotThrow(() -> validator.validate(stateToValidate));
    }

    @Test
    void shouldDoesNotThrowExceptionWhenValidateState2() {
        var stateToValidate = new State("São Paulo", "SP", new Country("123"));
        when(stateRepository.findByNameIgnoreCase(stateToValidate.getName()))
                .thenReturn(Optional.empty());

        when(stateRepository.findByAcronymIgnoreCase(stateToValidate.getAcronym()))
                .thenReturn(Optional.empty());

        String countryExternalId = stateToValidate.getCountry().getExternalId();
        when(countryRepository.existsByExternalId(countryExternalId))
                .thenReturn(true);

        assertDoesNotThrow(() -> validator.validate(stateToValidate));
    }

}