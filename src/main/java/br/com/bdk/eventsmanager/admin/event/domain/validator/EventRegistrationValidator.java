package br.com.bdk.eventsmanager.admin.event.domain.validator;

import br.com.bdk.eventsmanager.admin.event.domain.model.Event;
import br.com.bdk.eventsmanager.admin.event.domain.model.exception.DuplicateNameEventException;
import br.com.bdk.eventsmanager.admin.event.domain.repository.EventRepository;
import br.com.bdk.eventsmanager.core.exception.BusinessException;
import br.com.bdk.eventsmanager.core.validator.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventRegistrationValidator implements RegistrationValidator<Event> {

    private final EventRepository eventRepository;

    @Override
    public void validate(@NonNull Event event) throws BusinessException {
        var existingEventByNameAndCompany = eventRepository.findByNameIgnoreCaseAndCompanyExternalId(event.getName(), event.getCompany().getExternalId());
        if (existingEventByNameAndCompany.isPresent() && this.isNotSame(event, existingEventByNameAndCompany.get())) {
            throw new DuplicateNameEventException(event.getName());
        }
    }
}
