package br.com.bdk.eventsmanager.admin.country.domain.validator;

import br.com.bdk.eventsmanager.admin.country.domain.model.Country;
import br.com.bdk.eventsmanager.admin.country.domain.model.Country_;
import br.com.bdk.eventsmanager.admin.country.domain.model.exception.DuplicateAcronymCountryException;
import br.com.bdk.eventsmanager.admin.country.domain.model.exception.DuplicateNameCountryException;
import br.com.bdk.eventsmanager.admin.country.domain.repository.CountryRepository;
import org.junit.jupiter.api.DisplayName;
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
class CountryRegistrationValidatorTest {

    @InjectMocks
    private CountryRegistrationValidator validator;

    @Mock
    private CountryRepository countryRepository;

    @Test
    @DisplayName("Deve lançar exceção quando já existir um país com mesmo nome")
    void shouldThrowExceptionWhenDuplicateNameCountry() {
        var newCountry = new Country("NoMeTeStE", "aAa");
        var existingCountry = new Country("nometeste", "BbB");
        ReflectionTestUtils.setField(existingCountry, Country_.ID, 2L);

        when(countryRepository.findByNameIgnoreCase(newCountry.getName()))
                .thenReturn(Optional.of(existingCountry));

        var exception = assertThrows(DuplicateNameCountryException.class, () -> validator.validate(newCountry));
        assertThat(exception.getMessage()).isEqualTo("Já existe um país com nome '%s'".formatted(newCountry.getName()));
    }

    @Test
    @DisplayName("Deve lançar exceção quando já existir um país com mesma abreviação")
    void shouldThrowExceptionWhenDuplicateAcronymCountry() {
        var newCountry = new Country("NoMeTeSt", "bbb");
        var existingCountry = new Country("nometeste", "BbB");
        ReflectionTestUtils.setField(existingCountry, Country_.ID, 2L);

        when(countryRepository.findByNameIgnoreCase(newCountry.getName()))
                .thenReturn(Optional.empty());

        when(countryRepository.findByAcronymIgnoreCase(newCountry.getAcronym()))
                .thenReturn(Optional.of(existingCountry));

        var exception = assertThrows(DuplicateAcronymCountryException.class, () -> validator.validate(newCountry));
        assertThat(exception.getMessage()).isEqualTo("Já existe um país a sigla '%s'".formatted(newCountry.getAcronym()));
    }

    @Test
    @DisplayName("Não deve lançar exceção quando validar país")
    void shouldDoesNotThrowExceptionWhenValidateCountry() {
        long id = 2L;
        var newCountry = new Country("NOME_TESTE", "bbb");
        ReflectionTestUtils.setField(newCountry, Country_.ID, id);

        var existingCountry = new Country("NoMeTeSt", "BBB");
        ReflectionTestUtils.setField(existingCountry, Country_.ID, id);

        when(countryRepository.findByNameIgnoreCase(newCountry.getName()))
                .thenReturn(Optional.of(existingCountry));

        when(countryRepository.findByAcronymIgnoreCase(newCountry.getAcronym()))
                .thenReturn(Optional.of((newCountry)));

        assertDoesNotThrow(() -> validator.validate(newCountry));
    }
}