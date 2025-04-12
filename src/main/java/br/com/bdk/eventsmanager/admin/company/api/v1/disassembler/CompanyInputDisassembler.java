package br.com.bdk.eventsmanager.admin.company.api.v1.disassembler;

import br.com.bdk.eventsmanager.admin.city.domain.model.City;
import br.com.bdk.eventsmanager.admin.company.api.v1.model.input.CompanyInput;
import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.core.api.disassembler.InputDisassembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CompanyInputDisassembler implements InputDisassembler<CompanyInput, Company> {

    @Override
    public Company toEntity(@NonNull CompanyInput input) {
        var company = new Company();
        this.copyToEntity(input, company);
        return company;
    }

    @Override
    public void copyToEntity(@NonNull CompanyInput input, @NonNull Company company) {
        company.setName(input.getName());
        company.setBusinessName(input.getBusinessName());
        company.setDocument(input.getDocument());
        company.setStateRegistrationNumber(input.getStateRegistrationNumber());
        company.setCityRegistrationNumber(input.getCityRegistrationNumber());
        company.setEmail(input.getEmail());
        company.setPhoneNumber(input.getPhoneNumber());
        company.setActive(input.getActive());

        var address = company.getAddress();
        if (address != null) {
            address.setPostalCode(input.getPostalCode());
            address.setStreet(input.getStreet());
            address.setNeighborhood(input.getNeighborhood());
            address.setStreetNumber(input.getStreetNumber());
            address.setCity(new City(input.getCityId()));
            address.setComplement(input.getComplement());
        }
    }
}
