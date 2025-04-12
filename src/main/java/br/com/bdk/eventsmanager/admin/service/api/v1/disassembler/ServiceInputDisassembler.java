package br.com.bdk.eventsmanager.admin.service.api.v1.disassembler;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.service.api.v1.model.input.ServiceInput;
import br.com.bdk.eventsmanager.admin.service.domain.model.Service;
import br.com.bdk.eventsmanager.core.api.disassembler.InputDisassembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ServiceInputDisassembler implements InputDisassembler<ServiceInput, Service> {

    @Override
    public Service toEntity(@NonNull ServiceInput input) {
        final var service = new Service();
        this.copyToEntity(input, service);
        return service;
    }

    @Override
    public void copyToEntity(@NonNull ServiceInput input, @NonNull Service service) {
        service.setName(input.getName());
        service.setDescription(input.getDescription());
        service.setCostType(input.getCostType());
        service.setApplyBDI(input.getApplyBDI());
        service.setApplySellerCommission(input.getApplySellerCommission());
        service.setActive(input.getActive());
        service.setCompany(new Company(input.getCompanyId()));
    }
}
