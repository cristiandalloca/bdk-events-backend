package br.com.bdk.eventsmanager.admin.educationalinstitution.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.city.api.v1.assembler.CityStateModelAssembler;
import br.com.bdk.eventsmanager.admin.educationalinstitution.api.v1.model.EducationalInstitutionModel;
import br.com.bdk.eventsmanager.admin.educationalinstitution.domain.model.EducationalInstitution;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EducationalInstitutionModelAssembler implements ModelAssembler<EducationalInstitution, EducationalInstitutionModel> {

    private final CityStateModelAssembler cityStateModelAssembler;
    private final EducationalInstitutionTypeModelAssembler educationalInstitutionTypeModelAssembler;

    @NonNull
    @Override
    public EducationalInstitutionModel toModel(@NonNull EducationalInstitution educationalInstitution) {
        return EducationalInstitutionModel.builder()
                .id(educationalInstitution.getExternalId())
                .name(educationalInstitution.getName())
                .type(educationalInstitutionTypeModelAssembler.toModel(educationalInstitution.getType()))
                .active(educationalInstitution.isActive())
                .city(cityStateModelAssembler.toModel(educationalInstitution.getCity()))
                .build();
    }
}
