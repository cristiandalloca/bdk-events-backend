package br.com.bdk.eventsmanager.admin.educationalinstitution.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.admin.educationalinstitution.api.v1.model.EducationalInstitutionModel;
import br.com.bdk.eventsmanager.admin.educationalinstitution.api.v1.model.EducationalInstitutionTypeModel;
import br.com.bdk.eventsmanager.admin.educationalinstitution.api.v1.model.input.EducationalInstitutionInput;
import br.com.bdk.eventsmanager.admin.educationalinstitution.domain.model.EducationalInstitutionType;
import br.com.bdk.eventsmanager.core.api.model.PageModel;
import br.com.bdk.eventsmanager.core.api.openapi.ParameterQueryPageable;
import br.com.bdk.eventsmanager.core.api.openapi.RegisterController;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

@Tag(name = "Educational Institutions")
@SecurityRequirement(name = "bearerAuth")
public interface EducationalInstitutionRegisterControllerOpenApi extends RegisterController<EducationalInstitutionInput, EducationalInstitutionModel> {

    @Override
    @Operation(summary = "Create a educational institution")
    EducationalInstitutionModel create(EducationalInstitutionInput input);

    @Override
    @Operation(summary = "Find a educational institution by id")
    EducationalInstitutionModel findByExternalId(@Parameter(description = Description.EDUCATIONAL_INSTITUTION_IDENTIFIER, example = SpringDocConstantsUtil.Example.IDENTIFIER) String externalId);

    @Override
    @Operation(summary = "Update a educational institution by id")
    EducationalInstitutionModel updateByExternalId(@Parameter(description = Description.EDUCATIONAL_INSTITUTION_IDENTIFIER, example = SpringDocConstantsUtil.Example.IDENTIFIER) String externalId,
                                                   EducationalInstitutionInput input);

    @Override
    @Operation(summary = "Remove a educational institution by id")
    void removeByExternalId(String externalId);

    @Operation(summary = "Find all educational institution types")
    Collection<EducationalInstitutionTypeModel> findTypes();

    @Operation(summary = "Search educational institutions")
    @ParameterQueryPageable
    PageModel<EducationalInstitutionModel> findAll(@Parameter(description = Description.EDUCATIONAL_INSTITUTION_NAME) String name,
                                                   @Parameter(description = Description.CITY_IDENTIFIER) String cityExternalId,
                                                   @Parameter(description = Description.EDUCATIONAL_INSTITUTION_TYPE) EducationalInstitutionType type,
                                                   @Parameter(description = Description.EDUCATIONAL_INSTITUTION_ACTIVE) Boolean active,
                                                   @Parameter(hidden = true) Pageable pageable);

}
