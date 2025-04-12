package br.com.bdk.eventsmanager.admin.eventservice.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.eventservice.api.v1.model.EventServiceServiceModel;
import br.com.bdk.eventsmanager.admin.service.domain.model.Service;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class EventServiceServiceModelAssembler implements ModelAssembler<Service, EventServiceServiceModel> {

    @NonNull
    @Override
    public EventServiceServiceModel toModel(@NonNull Service service) {
        return EventServiceServiceModel.builder()
                .id(service.getExternalId())
                .name(service.getName())
                .applyBDI(service.isApplyBDI())
                .applySellerCommission(service.isApplySellerCommission())
                .costType(service.getCostType())
                .active(service.isActive())
                .build();
    }
}
