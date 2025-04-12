package br.com.bdk.eventsmanager.admin.country.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.country.api.v1.model.CountryModel;
import br.com.bdk.eventsmanager.admin.country.domain.model.Country;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CountryModelAssemblerTest {

    @InjectMocks
    private CountryModelAssembler countryModelAssembler;

    @Test
    void shouldAssembleCountryModel() {
        Country country = new Country("Brazil", "BRA");
        CountryModel countryModel = countryModelAssembler.toModel(country);
        assertThat(countryModel).isNotNull();
        assertThat(countryModel.getName()).isEqualTo(country.getName());
        assertThat(countryModel.getAcronym()).isEqualTo(country.getAcronym());
    }
}