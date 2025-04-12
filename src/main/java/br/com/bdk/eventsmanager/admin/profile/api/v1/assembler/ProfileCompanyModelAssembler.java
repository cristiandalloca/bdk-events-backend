package br.com.bdk.eventsmanager.admin.profile.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.profile.api.v1.model.ProfileCompanyModel;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ProfileCompanyModelAssembler implements ModelAssembler<Company, ProfileCompanyModel> {

    @NonNull
    @Override
    public ProfileCompanyModel toModel(@NonNull Company company) {
        return ProfileCompanyModel.builder()
                .id(company.getExternalId())
                .name(company.getName())
                .businessName(company.getBusinessName())
                .build();
    }
}
