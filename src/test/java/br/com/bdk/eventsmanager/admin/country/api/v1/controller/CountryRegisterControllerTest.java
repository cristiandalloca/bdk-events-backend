package br.com.bdk.eventsmanager.admin.country.api.v1.controller;

import br.com.bdk.eventsmanager.admin.country.api.v1.assembler.CountryModelAssembler;
import br.com.bdk.eventsmanager.admin.country.api.v1.disassembler.CountryInputDisassembler;
import br.com.bdk.eventsmanager.admin.country.api.v1.model.CountryModel;
import br.com.bdk.eventsmanager.admin.country.api.v1.model.input.CountryInput;
import br.com.bdk.eventsmanager.admin.country.domain.model.Country;
import br.com.bdk.eventsmanager.admin.country.domain.service.CountryRegisterService;
import br.com.bdk.eventsmanager.admin.state.api.v1.assembler.StateModelAssembler;
import br.com.bdk.eventsmanager.admin.state.api.v1.model.StateModel;
import br.com.bdk.eventsmanager.admin.state.domain.model.State;
import br.com.bdk.eventsmanager.admin.state.domain.service.StateRegisterService;
import br.com.bdk.eventsmanager.core.api.model.PageModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryRegisterControllerTest {

    @InjectMocks
    private CountryRegisterController controller;

    @Mock
    private CountryRegisterService countryRegisterService;

    @Mock
    private StateRegisterService stateRegisterService;

    @Mock
    private CountryModelAssembler countryModelAssembler;

    @Mock
    private CountryInputDisassembler countryInputDisassembler;

    @Mock
    private StateModelAssembler stateModelAssembler;


    @Test
    void shouldFindCountryByExternalId() {
        var externalId = "123-ABC";
        Country country = new Country("Brazil", "BRA");
        when(countryRegisterService.findByExternalId(externalId))
                .thenReturn(country);

        var expectedResult = CountryModel.builder()
                .name(country.getName())
                .acronym(country.getAcronym())
                .build();
        when(countryModelAssembler.toModel(country)).thenReturn(expectedResult);

        var result = controller.findByExternalId(externalId);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCreateCountry() {
        var newCountry = new Country("Japan", "JPN");
        var expectedResult = CountryModel.builder()
                .name(newCountry.getName())
                .acronym(newCountry.getAcronym())
                .build();
        when(countryInputDisassembler.toEntity(any(CountryInput.class))).thenReturn(newCountry);
        when(countryRegisterService.saveAndValidate(newCountry))
                .thenReturn(newCountry);
        when(countryModelAssembler.toModel(newCountry))
                .thenReturn(expectedResult);

        var result = controller.create(new CountryInput());
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldUpdateCountry() {
        var externalId = "123-ABC";
        var countryToUpdate = new Country("Japan", "JPN");
        var expectedResult = CountryModel.builder()
                .name(countryToUpdate.getName())
                .acronym(countryToUpdate.getAcronym())
                .build();
        when(countryRegisterService.findByExternalId(externalId)).thenReturn(countryToUpdate);
        when(countryRegisterService.saveAndValidate(countryToUpdate)).thenReturn(countryToUpdate);
        when(countryModelAssembler.toModel(countryToUpdate)).thenReturn(expectedResult);

        var result = controller.updateByExternalId(externalId, new CountryInput());
        assertThat(result).isEqualTo(expectedResult);
        verify(countryInputDisassembler, only()).copyToEntity(any(CountryInput.class), eq(countryToUpdate));
    }

    @Test
    void shouldFindAllCountries() {
        var countries = List.of(new Country("Brazil", "BRA"), new Country("Japan", "JPN"));
        var countriesPage = new PageImpl<>(countries);
        var page = Pageable.unpaged();
        when(countryRegisterService.findAll(null, page)).thenReturn(countriesPage);

        var expectedCountries = new PageModel<>(new PageImpl<>(List.of(CountryModel.builder().build())));
        when(countryModelAssembler.toPageModel(countriesPage)).thenReturn(expectedCountries);
        var result = controller.findAll(null, page);
        assertThat(result).isEqualTo(expectedCountries);
    }

    @Test
    void shouldFindAllStatesOfCountry() {
        var externalId = "123-ABC";
        var page = Pageable.unpaged();
        var states = List.of(new State("SP", "SP", new Country()), new State("SC", "SC", new Country()));
        var statesPage = new PageImpl<>(states);
        when(stateRegisterService.findAll(externalId, null, page)).thenReturn(statesPage);

        var expectedStates = new PageModel<>(new PageImpl<>(List.of(StateModel.builder().build())));
        when(stateModelAssembler.toPageModel(statesPage))
                .thenReturn(expectedStates);
        var result = controller.findAllStates(externalId, page);
        assertThat(result).isEqualTo(expectedStates);

    }
}