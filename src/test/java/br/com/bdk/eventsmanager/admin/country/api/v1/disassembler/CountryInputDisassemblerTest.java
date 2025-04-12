package br.com.bdk.eventsmanager.admin.country.api.v1.disassembler;

import br.com.bdk.eventsmanager.admin.country.api.v1.model.input.CountryInput;
import br.com.bdk.eventsmanager.admin.country.domain.model.Country;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class CountryInputDisassemblerTest {

    @InjectMocks
    private CountryInputDisassembler disassembler;

    @Test
    void shouldDisassembleCountryInput() {
        var countryInput = new CountryInput();
        var result = disassembler.toEntity(countryInput);
        assertThat(result).isNotNull();
    }

    @Test
    void shouldCopyCountryInputPropertiesToCountry() {
        var countryInput = new CountryInput();
        assertDoesNotThrow(()-> disassembler.copyToEntity(countryInput, new Country()));
    }
}