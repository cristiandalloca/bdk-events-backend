package br.com.bdk.eventsmanager.admin.supplier.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.supplier.api.v1.model.SupplierCompanyModel;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class SupplierCompanyModelAssembler implements ModelAssembler<Company, SupplierCompanyModel> {

    @NonNull
    @Override
    public SupplierCompanyModel toModel(@NonNull Company company) {
        return SupplierCompanyModel.builder()
                .id(company.getExternalId())
                .name(company.getName())
                .businessName(company.getBusinessName())
                .build();
    }
}
