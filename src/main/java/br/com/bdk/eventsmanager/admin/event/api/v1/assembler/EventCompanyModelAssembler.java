package br.com.bdk.eventsmanager.admin.event.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.event.api.v1.model.EventCompanyModel;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class EventCompanyModelAssembler implements ModelAssembler<Company, EventCompanyModel> {

    @NonNull
    @Override
    public EventCompanyModel toModel(@NonNull Company company) {
        return EventCompanyModel.builder()
                .id(company.getExternalId())
                .name(company.getName())
                .businessName(company.getBusinessName())
                .build();
    }
}
