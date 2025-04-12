package br.com.bdk.eventsmanager.admin.state.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.country.api.v1.assembler.CountryModelAssembler;
import br.com.bdk.eventsmanager.admin.country.api.v1.model.CountryModel;
import br.com.bdk.eventsmanager.admin.country.domain.model.Country;
import br.com.bdk.eventsmanager.admin.state.domain.model.State;
import br.com.bdk.eventsmanager.admin.state.domain.model.State_;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StateCountryModelAssemblerTest {

    @InjectMocks
    private StateCountryModelAssembler assembler;

    @Mock
    private CountryModelAssembler countryModelAssembler;

    @Test
    void shouldAssembleStateCountryModel() {
        Country country = new Country("Brazil", "BRA");
        State state = new State("Santa Catarina", "SC", country);
        ReflectionTestUtils.setField(state, State_.EXTERNAL_ID, "123-ABCSD");
        CountryModel countryModel = CountryModel.builder()
                .name(state.getCountry().getName())
                .acronym(state.getCountry().getAcronym())
                .build();
        when(countryModelAssembler.toModel(country)).thenReturn(countryModel);

        var result = assembler.toModel(state);
        assertThat(result.getId()).isEqualTo(state.getExternalId());
        assertThat(result.getName()).isEqualTo(state.getName());
        assertThat(result.getAcronym()).isEqualTo(state.getAcronym());
        assertThat(result.getCountry().getName()).isEqualTo(state.getCountry().getName());
        assertThat(result.getCountry().getAcronym()).isEqualTo(state.getCountry().getAcronym());
    }
}