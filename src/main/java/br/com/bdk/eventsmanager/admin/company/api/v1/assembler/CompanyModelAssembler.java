package br.com.bdk.eventsmanager.admin.company.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.city.api.v1.assembler.CityStateModelAssembler;
import br.com.bdk.eventsmanager.admin.company.api.v1.model.CompanyModel;
import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyModelAssembler implements ModelAssembler<Company, CompanyModel> {

    private final CityStateModelAssembler cityStateModelAssembler;

    @NonNull
    @Override
    public CompanyModel toModel(@NonNull Company company) {
        var companyModel = CompanyModel.builder()
                .id(company.getExternalId())
                .name(company.getName())
                .businessName(company.getBusinessName())
                .email(company.getEmail())
                .phoneNumber(company.getPhoneNumber())
                .document(company.getDocument())
                .stateRegistrationNumber(company.getStateRegistrationNumber())
                .cityRegistrationNumber(company.getCityRegistrationNumber())
                .active(company.isActive());

        var address = company.getAddress();
        if (address != null) {
            companyModel
                    .postalCode(address.getPostalCode())
                    .street(address.getStreet())
                    .streetNumber(address.getStreetNumber())
                    .neighborhood(address.getNeighborhood())
                    .complement(address.getComplement())
                    .city(cityStateModelAssembler.toModel(address.getCity()));
        }
        return companyModel.build();
    }
}
