package br.com.bdk.eventsmanager.admin.event.domain.validator;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.event.domain.model.Event;
import br.com.bdk.eventsmanager.admin.event.domain.model.exception.DuplicateNameEventException;
import br.com.bdk.eventsmanager.admin.event.domain.repository.EventRepository;
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
class EventRegistrationValidatorTest {

    @InjectMocks
    private EventRegistrationValidator eventRegisterValidator;

    @Mock
    private EventRepository eventRepository;

    @Test
    void shouldValidateWhenNotExistingEventWithSameName() {
        Event event = new Event();
        event.setName("Jantar dançante");
        event.setCompany(new Company("321-ABC"));
        when(eventRepository.findByNameIgnoreCaseAndCompanyExternalId(event.getName(), event.getCompany().getExternalId()))
                .thenReturn(Optional.empty());
        assertDoesNotThrow(() -> eventRegisterValidator.validate(event));
    }

    @Test
    void shouldValidateWhenExistingEventButIsSameEvent() {
        Event event = new Event();
        ReflectionTestUtils.setField(event, "id", 1L);
        event.setName("Jantar a luz de velas");
        event.setCompany(new Company("1L"));
        when(eventRepository.findByNameIgnoreCaseAndCompanyExternalId(event.getName(), event.getCompany().getExternalId()))
                .thenReturn(Optional.of(event));
        assertDoesNotThrow(() -> eventRegisterValidator.validate(event));
    }

    @Test
    void shouldThrowExceptionWhenExistingEventByName() {
        Event event = new Event();
        ReflectionTestUtils.setField(event, "id", 1L);
        event.setName("Mágico");
        event.setCompany(new Company("1L"));
        when(eventRepository.findByNameIgnoreCaseAndCompanyExternalId(event.getName(), event.getCompany().getExternalId()))
                .thenReturn(Optional.of(new Event()));
        var exception = assertThrows(DuplicateNameEventException.class, () -> eventRegisterValidator.validate(event));
        assertEquals("Já existe um evento com nome '%s'".formatted(event.getName()), exception.getMessage());
    }
}