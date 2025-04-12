package br.com.bdk.eventsmanager.admin.eventservice.domain.service;

import br.com.bdk.eventsmanager.admin.company.invitationtype.domain.service.CompanyInvitationTypeRegisterService;
import br.com.bdk.eventsmanager.admin.event.domain.service.EventRegistrationService;
import br.com.bdk.eventsmanager.admin.eventservice.domain.model.EventService;
import br.com.bdk.eventsmanager.admin.eventservice.domain.model.EventServiceMeasurementType;
import br.com.bdk.eventsmanager.admin.eventservice.domain.model.exception.EventServiceNotFoundException;
import br.com.bdk.eventsmanager.admin.eventservice.domain.repository.EventServiceRepository;
import br.com.bdk.eventsmanager.admin.eventservice.domain.validator.EventServiceRegistrationValidator;
import br.com.bdk.eventsmanager.admin.service.domain.service.ServiceRegistrationService;
import br.com.bdk.eventsmanager.core.exception.ResourceInUseException;
import br.com.bdk.eventsmanager.core.service.RegistrationService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.stripToNull;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class EventServiceRegistrationService implements RegistrationService<EventService> {

    private final EventRegistrationService eventRegisterService;
    private final ServiceRegistrationService serviceRegisterService;
    private final EventServiceRepository eventServiceRepository;
    private final EventServiceRegistrationValidator eventServiceRegisterValidator;
    private final CompanyInvitationTypeRegisterService companyInvitationTypeRegisterService;
    private static final String EXCEPTION_MESSAGE_EVENT_SERVICE_IN_USE = "Serviço do evento '%s' não pode ser removido, pois está em uso";

    @NonNull
    @Transactional(readOnly = true)
    public Page<EventService> findAllByEventExternalId(@NonNull String eventExternalId, @Nullable String serviceName, @NonNull Pageable pageable) {
        return eventServiceRepository.findAllByEventExternalId(eventExternalId, stripToNull(serviceName), pageable);
    }

    @NonNull
    @Override
    @Transactional
    public EventService validateAndSave(@NonNull EventService eventService) {
        eventServiceRegisterValidator.validate(eventService);
        eventService.setEvent(eventRegisterService.findByExternalId(eventService.getEvent().getExternalId()));
        eventService.setService(serviceRegisterService.findByExternalId(eventService.getService().getExternalId()));
        if (EventServiceMeasurementType.NUMBER_OF_INVITATIONS.equals(eventService.getMeasurementType())) {
            final var eventCompany = eventService.getEvent().getCompany();
            eventService.setCompanyInvitationType(companyInvitationTypeRegisterService.findByExternalIdAndCompanyExternalId(eventService.getCompanyInvitationType().getExternalId(), eventCompany.getExternalId()));
        } else {
            eventService.setCompanyInvitationType(null);
        }

        return eventServiceRepository.save(eventService);
    }

    @Transactional
    public void addServicesToEvent(@NonNull String eventExternalId, @NotEmpty List<@NotBlank String> servicesIds) {
        final var event = eventRegisterService.findByExternalId(eventExternalId);
        List<EventService> eventServices = new ArrayList<>();
        servicesIds.forEach(serviceId -> {
            final var service = serviceRegisterService.findByExternalId(serviceId);
            final var eventService = new EventService(event, service);
            eventServiceRegisterValidator.validate(eventService);
            eventServices.add(eventService);
        });

        eventServiceRepository.saveAll(eventServices);
    }

    @NonNull
    @Override
    @Transactional(readOnly = true)
    public EventService findByExternalId(@NonNull String eventServiceExternalId) {
        return this.findByExternalIdOrFail(eventServiceExternalId);
    }

    @Override
    @Transactional
    public void removeByExternalId(@NonNull String eventServiceExternalId) {
        try {
            eventServiceRepository.delete(this.findByExternalIdOrFail(eventServiceExternalId));
            eventServiceRepository.flush();
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            throw new ResourceInUseException(EXCEPTION_MESSAGE_EVENT_SERVICE_IN_USE.formatted(eventServiceExternalId));
        }
    }

    private EventService findByExternalIdOrFail(@NonNull String eventServiceExternalId) {
        return eventServiceRepository.findByExternalId(eventServiceExternalId)
                .orElseThrow(() -> new EventServiceNotFoundException(eventServiceExternalId));
    }

}
