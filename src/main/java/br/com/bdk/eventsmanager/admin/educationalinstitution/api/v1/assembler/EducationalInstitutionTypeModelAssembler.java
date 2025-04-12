package br.com.bdk.eventsmanager.admin.educationalinstitution.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.educationalinstitution.api.v1.model.EducationalInstitutionTypeModel;
import br.com.bdk.eventsmanager.admin.educationalinstitution.domain.model.EducationalInstitutionType;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EducationalInstitutionTypeModelAssembler implements ModelAssembler<EducationalInstitutionType, EducationalInstitutionTypeModel> {

    @NonNull
    @Override
    public EducationalInstitutionTypeModel toModel(@NonNull EducationalInstitutionType type) {
        return EducationalInstitutionTypeModel.builder()
                .id(type.name())
                .description(type.getDescription())
                .build();
    }
}
