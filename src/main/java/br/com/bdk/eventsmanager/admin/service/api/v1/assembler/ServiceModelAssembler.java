package br.com.bdk.eventsmanager.admin.service.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.service.api.v1.model.ServiceModel;
import br.com.bdk.eventsmanager.admin.service.domain.model.Service;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ServiceModelAssembler implements ModelAssembler<Service, ServiceModel> {

    @NonNull
    @Override
    public ServiceModel toModel(@NonNull Service service) {
        final var company = service.getCompany();
        return ServiceModel.builder()
                .id(service.getExternalId())
                .name(service.getName())
                .description(service.getDescription())
                .applyBDI(service.isApplyBDI())
                .applySellerCommission(service.isApplySellerCommission())
                .costType(service.getCostType())
                .active(service.isActive())
                .company(ServiceModel.ServiceCompanyModel.builder()
                        .id(company.getExternalId())
                        .name(company.getName())
                        .businessName(company.getBusinessName())
                        .build())
                .build();
    }
}
