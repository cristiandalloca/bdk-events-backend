package br.com.bdk.eventsmanager.admin.city.domain.service;

import br.com.bdk.eventsmanager.admin.city.domain.model.City;
import br.com.bdk.eventsmanager.admin.city.domain.model.exception.CityNotFoundException;
import br.com.bdk.eventsmanager.admin.city.domain.repository.CityRepository;
import br.com.bdk.eventsmanager.admin.city.domain.validator.CityRegistrationValidator;
import br.com.bdk.eventsmanager.admin.state.domain.model.State;
import br.com.bdk.eventsmanager.admin.state.domain.service.StateRegisterService;
import br.com.bdk.eventsmanager.core.exception.BusinessException;
import br.com.bdk.eventsmanager.core.exception.ResourceInUseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityRegistrationServiceTest {

    private static final String EXTERNAL_ID = "123";
    private static final String STATE_EXTERNAL_ID = "456";

    @InjectMocks
    private CityRegistrationService cityRegisterService;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private CityRegistrationValidator cityRegisterValidator;

    @Mock
    private StateRegisterService stateRegisterService;

    @Captor
    private ArgumentCaptor<String> filters;

    @Test
    void shouldThrowExceptionWhenFindByExternalId() {
        when(cityRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.empty());

        var exception = assertThrows(CityNotFoundException.class,
                () -> cityRegisterService.findByExternalId(EXTERNAL_ID));

        assertEquals("Não foi encontrada uma cidade com código '%s'".formatted(EXTERNAL_ID), exception.getMessage());
    }

    @Test
    void shouldFindByExternalId() {
        City city = new City();
        when(cityRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(city));

        City result = cityRegisterService.findByExternalId(EXTERNAL_ID);

        assertEquals(city, result);
    }

    @Test
    void shouldValidateAndSave() {
        City city = new City(EXTERNAL_ID);
        State state = new State(STATE_EXTERNAL_ID);
        city.setState(state);
        when(stateRegisterService.findByExternalId(STATE_EXTERNAL_ID)).thenReturn(state);

        cityRegisterService.validateAndSave(city);

        verify(cityRegisterValidator, times(1)).validate(city);
        verify(cityRepository, times(1)).save(city);
    }

    @Test
    void shouldThrowExceptionWhenValidateCity() {
        City city = new City(EXTERNAL_ID);
        String exceptionMessage = "Teste";
        doThrow(new BusinessException(exceptionMessage)).when(cityRegisterValidator).validate(city);

        var exception = assertThrowsExactly(BusinessException.class,
                () -> cityRegisterService.validateAndSave(city));

        assertEquals(exception.getMessage(), exceptionMessage);

        verify(cityRepository, never()).save(any(City.class));
    }

    @Test
    void shouldThrowExceptionWhenRemoveByExternalId() {
        City city = new City();
        when(cityRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(city));

        String exceptionMessage = "Teste";
        doThrow(new DataIntegrityViolationException(exceptionMessage)).when(cityRepository).flush();

        var exception = assertThrows(ResourceInUseException.class,
                () -> cityRegisterService.removeByExternalId(EXTERNAL_ID));
        assertEquals("Cidade de código '%s' não pode ser removida, pois está em uso".formatted(EXTERNAL_ID), exception.getMessage());
    }

    @Test
    void shouldFindAll() {
        Pageable page = Pageable.unpaged();
        cityRegisterService.findAll(" ", "", page);

        verify(cityRepository, times(1)).findAllByStateExternalIdAndName(filters.capture(), filters.capture(), eq(page));
        assertThat(filters.getAllValues()).containsOnlyNulls();
    }
}