package br.com.bdk.eventsmanager.admin.profile.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.admin.profile.api.v1.model.ProfileCompleteModel;
import br.com.bdk.eventsmanager.admin.profile.api.v1.model.ProfileModel;
import br.com.bdk.eventsmanager.admin.profile.api.v1.model.input.ProfileInput;
import br.com.bdk.eventsmanager.core.api.model.PageModel;
import br.com.bdk.eventsmanager.core.api.openapi.ParameterQueryPageable;
import br.com.bdk.eventsmanager.core.api.openapi.RegisterController;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;

@Tag(name = "Profiles")
@SecurityRequirement(name = "bearerAuth")
public interface ProfileRegisterControllerOpenApi extends RegisterController<ProfileInput, ProfileCompleteModel> {

    @Override
    @Operation(summary = "Create a profile")
    ProfileCompleteModel create(ProfileInput input);

    @Override
    @Operation(summary = "Find a profile by id")
    ProfileCompleteModel findByExternalId(@Parameter(description = Description.PROFILE_IDENTIFIER, example = Example.IDENTIFIER) String externalId);

    @Override
    @Operation(summary = "Update a profile by id")
    ProfileCompleteModel updateByExternalId(@Parameter(description = Description.PROFILE_IDENTIFIER, example = Example.IDENTIFIER) String externalId,
                                            ProfileInput input);

    @Override
    @Operation(summary = "Remove a profile by id")
    void removeByExternalId(@Parameter(description = Description.PROFILE_IDENTIFIER, example = Example.IDENTIFIER) String externalId);

    @Operation(summary = "Search profiles")
    @ParameterQueryPageable
    PageModel<ProfileModel> findAll(@Parameter(description = Description.PROFILE_NAME) String name,
                                    @Parameter(description = Description.COMPANY_IDENTIFIER) String companyExternalId,
                                    @Parameter(hidden = true) Pageable pageable);

}
