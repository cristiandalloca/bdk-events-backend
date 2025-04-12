package br.com.bdk.eventsmanager.admin.user.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.user.api.v1.model.UserCompanyModel;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserCompanyModelAssembler implements ModelAssembler<Company, UserCompanyModel> {

    @NonNull
    @Override
    public UserCompanyModel toModel(@NonNull Company company) {
        return UserCompanyModel.builder()
                .id(company.getExternalId())
                .name(company.getName())
                .businessName(company.getBusinessName())
                .build();
    }
}
