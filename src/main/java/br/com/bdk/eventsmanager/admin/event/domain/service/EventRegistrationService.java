package br.com.bdk.eventsmanager.admin.event.domain.service;

import br.com.bdk.eventsmanager.admin.company.domain.service.CompanyRegistrationService;
import br.com.bdk.eventsmanager.admin.event.domain.model.Event;
import br.com.bdk.eventsmanager.admin.event.domain.model.EventType;
import br.com.bdk.eventsmanager.admin.event.domain.model.exception.EventNotFoundException;
import br.com.bdk.eventsmanager.admin.event.domain.repository.EventRepository;
import br.com.bdk.eventsmanager.admin.event.domain.validator.EventRegistrationValidator;
import br.com.bdk.eventsmanager.core.exception.ResourceInUseException;
import br.com.bdk.eventsmanager.core.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.commons.lang3.StringUtils.trimToNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventRegistrationService implements RegistrationService<Event> {

    private final CompanyRegistrationService companyRegistrationService;
    private final EventRepository eventRepository;
    private final EventRegistrationValidator eventRegisterValidator;
    private static final String EXCEPTION_MESSAGE_EVENT_IN_USE
            = "Evento de código '%s' não pode ser removido, pois está em uso";

    @Override
    @NonNull
    @Transactional
    public Event validateAndSave(@NonNull @Valid Event event) {
        eventRegisterValidator.validate(event);
        event.setCompany(companyRegistrationService.findByExternalId(event.getCompany().getExternalId()));
        return eventRepository.save(event);
    }

    @NonNull
    @Override
    @Transactional(readOnly = true)
    public Event findByExternalId(@NonNull String externalId) {
        return this.findByExternalIdOrFail(externalId);
    }

    private Event findByExternalIdOrFail(String externalId) {
        return eventRepository.findByExternalId(externalId)
                .orElseThrow(() -> new EventNotFoundException(externalId));
    }

    @Override
    @Transactional
    public void removeByExternalId(@NonNull String externalId) {
        try {
            eventRepository.delete(this.findByExternalIdOrFail(externalId));
            eventRepository.flush();
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            throw new ResourceInUseException(EXCEPTION_MESSAGE_EVENT_IN_USE.formatted(externalId));
        }

    }

    @NonNull
    @Transactional(readOnly = true)
    public Page<Event> findAll(@Nullable String name, @Nullable String companyExternalId, @Nullable EventType type, @Nullable Boolean active, @NonNull Pageable pageable) {
        return eventRepository.findAllByNameAndTypeAndActive(trimToNull(name), trimToNull(companyExternalId), type, active, pageable);
    }
}
