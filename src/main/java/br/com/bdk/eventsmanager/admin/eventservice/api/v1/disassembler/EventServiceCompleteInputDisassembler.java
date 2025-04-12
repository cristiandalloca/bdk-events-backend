package br.com.bdk.eventsmanager.admin.eventservice.api.v1.disassembler;

import br.com.bdk.eventsmanager.admin.company.invitationtype.domain.model.CompanyInvitationType;
import br.com.bdk.eventsmanager.admin.event.domain.model.Event;
import br.com.bdk.eventsmanager.admin.eventservice.api.v1.model.input.EventServiceCompleteInput;
import br.com.bdk.eventsmanager.admin.eventservice.domain.model.EventService;
import br.com.bdk.eventsmanager.admin.service.domain.model.Service;
import br.com.bdk.eventsmanager.core.api.disassembler.InputDisassembler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class EventServiceCompleteInputDisassembler implements InputDisassembler<EventServiceCompleteInput, EventService> {

    @Override
    public EventService toEntity(@NonNull EventServiceCompleteInput input) {
        var eventService = new EventService();
        this.copyToEntity(input, eventService);
        return eventService;
    }

    @Override
    public void copyToEntity(EventServiceCompleteInput input, EventService eventService) {
        eventService.setEvent(new Event(input.getEventId()));
        eventService.setService(new Service(input.getServiceId()));
        eventService.setPrice(input.getPrice());
        eventService.setMinimumPrice(input.getMinimumPrice());
        eventService.setQuantity(input.getQuantity());
        eventService.setDisplayInProposal(input.getDisplayInProposal());
        eventService.setDisplayPriceInProposal(input.getDisplayPriceInProposal());
        eventService.setAllowChangeQuantity(input.getAllowChangeQuantity());
        eventService.setAllowChangePrice(input.getAllowChangePrice());
        eventService.setMeasurementType(input.getMeasurementType());
        if (StringUtils.isNotBlank(input.getCompanyInvitationTypeId())) {
            eventService.setCompanyInvitationType(new CompanyInvitationType(input.getCompanyInvitationTypeId()));
        }
    }
}
