package br.com.bdk.eventsmanager.admin.eventservice.domain.validator;

import br.com.bdk.eventsmanager.admin.event.domain.service.EventRegistrationService;
import br.com.bdk.eventsmanager.admin.eventservice.domain.model.EventService;
import br.com.bdk.eventsmanager.admin.eventservice.domain.model.EventServiceMeasurementType;
import br.com.bdk.eventsmanager.admin.eventservice.domain.model.exception.DuplicateEventServiceException;
import br.com.bdk.eventsmanager.admin.eventservice.domain.model.exception.EventServiceCompanyNotEqualsException;
import br.com.bdk.eventsmanager.admin.eventservice.domain.model.exception.EventServiceMeasurementTypeQuantityException;
import br.com.bdk.eventsmanager.admin.eventservice.domain.repository.EventServiceRepository;
import br.com.bdk.eventsmanager.admin.service.domain.service.ServiceRegistrationService;
import br.com.bdk.eventsmanager.core.exception.BusinessException;
import br.com.bdk.eventsmanager.core.validator.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Component
@RequiredArgsConstructor
public class EventServiceRegistrationValidator implements RegistrationValidator<EventService> {

    private final EventServiceRepository eventServiceRepository;
    private final EventRegistrationService eventRegisterService;
    private final ServiceRegistrationService serviceRegisterService;

    @Override
    @Transactional(readOnly = true)
    public void validate(@NonNull EventService eventService) throws BusinessException {
        final var eventExternalId = eventService.getEvent().getExternalId();
        final var serviceExternalId = eventService.getService().getExternalId();
        var existingEventService = eventServiceRepository.findByEventExternalIdAndServiceExternalId(eventExternalId, serviceExternalId);
        if (existingEventService.isPresent() && this.isNotSame(eventService, existingEventService.get())) {
            throw new DuplicateEventServiceException(eventExternalId, serviceExternalId);
        }

        final var event = eventRegisterService.findByExternalId(eventExternalId);
        final var service = serviceRegisterService.findByExternalId(serviceExternalId);
        if (!event.getCompany().equals(service.getCompany())) {
            throw new EventServiceCompanyNotEqualsException(eventExternalId, serviceExternalId);
        }

        if (EventServiceMeasurementType.UNIQUE.equals(eventService.getMeasurementType())
                && !eventService.isAllowChangePrice() && eventService.getPrice() == null) {
            throw new BusinessException("O preço é obrigatório pois não é permitido que seja alterado o preço na proposta");
        }

        if (EventServiceMeasurementType.UNIQUE.equals(eventService.getMeasurementType())
                && !eventService.isAllowChangeQuantity() && (eventService.getQuantity() == null || eventService.getQuantity().compareTo(INTEGER_ZERO) <= 0)) {
            throw new BusinessException("A quantidade deve ser maior que zero, pois não é permitido que seja alterado a quantidade na proposta");
        }

        if (EventServiceMeasurementType.NUMBER_OF_INVITATIONS.equals(eventService.getMeasurementType())
                && eventService.getCompanyInvitationType() == null) {
            throw new BusinessException("É necessário vincular um tipo de convite para a unidade de medida '%s'".formatted(eventService.getMeasurementType().getDescription()));
        }

        if (eventService.getPrice() != null && eventService.getMinimumPrice() != null
                && eventService.getPrice().compareTo(eventService.getMinimumPrice()) < 0) {
            throw new BusinessException("O preço informado deve ser maior ou igual ao preço mínimo");
        }

        if (eventService.getMeasurementType().isNumberOfPeopleOrInvitations()
                && ((eventService.getQuantity() != null && eventService.getQuantity().compareTo(INTEGER_ZERO) > 0)
                || (eventService.isAllowChangeQuantity()))) {
            throw new EventServiceMeasurementTypeQuantityException(eventService.getMeasurementType());
        }


    }

}
