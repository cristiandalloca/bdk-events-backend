package br.com.bdk.eventsmanager.admin.eventservice.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.company.invitationtype.api.v1.assembler.CompanyInvitationTypeModelAssembler;
import br.com.bdk.eventsmanager.admin.eventservice.api.v1.model.EventServiceCompleteModel;
import br.com.bdk.eventsmanager.admin.eventservice.domain.model.EventService;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventServiceCompleteModelAssembler implements ModelAssembler<EventService, EventServiceCompleteModel> {

    private final EventServiceServiceModelAssembler eventServiceServiceModelAssembler;
    private final EventServiceMeasurementTypeModelAssembler eventServiceMeasurementTypeModelAssembler;
    private final EventServiceEventModelAssembler eventServiceEventModelAssembler;
    private final CompanyInvitationTypeModelAssembler companyInvitationTypeModelAssembler;

    @NonNull
    @Override
    public EventServiceCompleteModel toModel(@NonNull EventService eventService) {
        return EventServiceCompleteModel.builder()
                .id(eventService.getExternalId())
                .event(eventServiceEventModelAssembler.toModel(eventService.getEvent()))
                .service(eventServiceServiceModelAssembler.toModel(eventService.getService()))
                .price(eventService.getPrice())
                .quantity(eventService.getQuantity())
                .displayInProposal(eventService.isDisplayInProposal())
                .displayPriceInProposal(eventService.isDisplayPriceInProposal())
                .allowChangeQuantity(eventService.isAllowChangeQuantity())
                .allowChangePrice(eventService.isAllowChangePrice())
                .measurementType(eventServiceMeasurementTypeModelAssembler.toModel(eventService.getMeasurementType()))
                .companyInvitationType(eventService.getCompanyInvitationType() != null ? companyInvitationTypeModelAssembler.toModel(eventService.getCompanyInvitationType()) : null)
                .build();
    }

}
