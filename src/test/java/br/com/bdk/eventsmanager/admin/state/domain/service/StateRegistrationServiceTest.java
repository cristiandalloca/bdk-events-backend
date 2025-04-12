package br.com.bdk.eventsmanager.admin.state.domain.service;

import br.com.bdk.eventsmanager.admin.country.domain.model.Country;
import br.com.bdk.eventsmanager.admin.country.domain.service.CountryRegisterService;
import br.com.bdk.eventsmanager.admin.state.domain.model.State;
import br.com.bdk.eventsmanager.admin.state.domain.model.exception.StateNotFoundException;
import br.com.bdk.eventsmanager.admin.state.domain.repository.StateRepository;
import br.com.bdk.eventsmanager.admin.state.domain.validator.StateRegistrationValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StateRegistrationServiceTest {

    @InjectMocks
    private StateRegisterService stateRegisterService;

    @Mock
    private StateRepository stateRepository;

    @Mock
    private StateRegistrationValidator stateRegisterValidator;

    @Mock
    private CountryRegisterService countryRegisterService;


    @Test
    void shouldDoesNotThrowExceptionWhenFindByExternalId() {
        String externalId = "123-abc-def";
        State stateFound = new State();
        when(stateRepository.findByExternalId(externalId)).thenReturn(Optional.of(stateFound));
        var result = stateRegisterService.findByExternalId(externalId);
        assertEquals(result, stateFound);
        verifyNoInteractions(stateRegisterValidator, countryRegisterService);

    }

    @Test
    void shouldThrowExceptionWhenFindByExternalIdNotFound() {
        String externalId = "123-abc-def";
        when(stateRepository.findByExternalId(externalId)).thenReturn(Optional.empty());
        assertThrowsExactly(StateNotFoundException.class, () -> stateRegisterService.findByExternalId(externalId),
                "Não foi encontrado um estado com id %s".formatted(externalId));
        verifyNoInteractions(stateRegisterValidator, countryRegisterService);
    }

    @Test
    void shouldValidateAndSaveState() {
        Country country = new Country("123");
        State state = new State("São Paulo", "SP", country);
        when(countryRegisterService.findByExternalId(state.getCountry().getExternalId()))
                .thenReturn(country);
        when(stateRepository.save(state)).thenReturn(state);

        var result = stateRegisterService.saveAndValidate(state);
        assertEquals(result, state);
        inOrder(stateRegisterValidator, countryRegisterService, stateRepository);
    }

    @Test
    void shouldFindAllStatesWhenCountryExternalIdIsNotNull() {
        Pageable unpaged = Pageable.unpaged();
        String countryExternalId = "123-ABC";
        String stateName = "123";
        when(stateRepository.findAllByCountryExternalIdAndName(countryExternalId, stateName, unpaged))
                .thenReturn(Page.empty());

        var result = stateRegisterService.findAll(countryExternalId, stateName, unpaged);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(stateRepository, never()).findAll(unpaged);
    }

}