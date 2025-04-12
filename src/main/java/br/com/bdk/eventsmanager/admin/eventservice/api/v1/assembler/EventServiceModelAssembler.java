package br.com.bdk.eventsmanager.admin.eventservice.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.company.invitationtype.api.v1.assembler.CompanyInvitationTypeModelAssembler;
import br.com.bdk.eventsmanager.admin.company.invitationtype.api.v1.model.CompanyInvitationTypeModel;
import br.com.bdk.eventsmanager.admin.eventservice.api.v1.model.EventServiceModel;
import br.com.bdk.eventsmanager.admin.eventservice.domain.model.EventService;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventServiceModelAssembler implements ModelAssembler<EventService, EventServiceModel> {

    private final EventServiceServiceModelAssembler eventServiceServiceModelAssembler;
    private final EventServiceMeasurementTypeModelAssembler eventServiceMeasurementTypeModelAssembler;
    private final CompanyInvitationTypeModelAssembler companyInvitationTypeModelAssembler;

    @NonNull
    @Override
    public EventServiceModel toModel(@NonNull EventService eventService) {
        CompanyInvitationTypeModel companyInvitationTypeModel = null;
        if (eventService.getCompanyInvitationType() != null) {
            companyInvitationTypeModel = companyInvitationTypeModelAssembler.toModel(eventService.getCompanyInvitationType());
        }

        return EventServiceModel.builder()
                .id(eventService.getExternalId())
                .service(eventServiceServiceModelAssembler.toModel(eventService.getService()))
                .price(eventService.getPrice())
                .minimumPrice(eventService.getMinimumPrice())
                .quantity(eventService.getQuantity())
                .displayInProposal(eventService.isDisplayInProposal())
                .displayPriceInProposal(eventService.isDisplayPriceInProposal())
                .allowChangeQuantity(eventService.isAllowChangeQuantity())
                .allowChangePrice(eventService.isAllowChangePrice())
                .measurementType(eventServiceMeasurementTypeModelAssembler.toModel(eventService.getMeasurementType()))
                .companyInvitationType(companyInvitationTypeModel)
                .build();
    }

}
