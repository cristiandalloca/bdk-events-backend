package br.com.bdk.eventsmanager.admin.educationalinstitution.api.v1.disassembler;

import br.com.bdk.eventsmanager.admin.city.domain.model.City;
import br.com.bdk.eventsmanager.admin.educationalinstitution.api.v1.model.input.EducationalInstitutionInput;
import br.com.bdk.eventsmanager.admin.educationalinstitution.domain.model.EducationalInstitution;
import br.com.bdk.eventsmanager.core.api.disassembler.InputDisassembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class EducationalInstitutionInputDisassembler implements InputDisassembler<EducationalInstitutionInput, EducationalInstitution> {

    @Override
    public EducationalInstitution toEntity(@NonNull EducationalInstitutionInput input) {
        var educationalInstitution = new EducationalInstitution();
        this.copyToEntity(input, educationalInstitution);
        return educationalInstitution;
    }

    @Override
    public void copyToEntity(@NonNull EducationalInstitutionInput input, @NonNull EducationalInstitution educationalInstitution) {
        educationalInstitution.setName(input.getName());
        educationalInstitution.setActive(input.getActive());
        educationalInstitution.setType(input.getType());
        educationalInstitution.setCity(new City(input.getCityId()));
    }
}
