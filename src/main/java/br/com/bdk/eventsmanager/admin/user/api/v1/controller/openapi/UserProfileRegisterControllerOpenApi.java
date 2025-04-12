package br.com.bdk.eventsmanager.admin.user.api.v1.controller.openapi;


import br.com.bdk.eventsmanager.admin.user.api.v1.model.UserProfileModel;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Collection;

@Tag(name = "Users")
@SecurityRequirement(name = "bearerAuth")
public interface UserProfileRegisterControllerOpenApi {

    @Operation(summary = "Find profiles for user")
    Collection<UserProfileModel> findProfiles(@Parameter(description = Description.USER_IDENTIFIER, example = Example.IDENTIFIER) String userExternalId);

    @Operation(summary = "Add a profile for user")
    void addProfile(@Parameter(description = Description.USER_IDENTIFIER, example = Example.IDENTIFIER) String userExternalId,
                    @Parameter(description = Description.PROFILE_IDENTIFIER, example = Example.IDENTIFIER) String profileId);

    @Operation(summary = "Remove a profile for user")
    void removeProfile(@Parameter(description = Description.USER_IDENTIFIER, example = Example.IDENTIFIER) String userExternalId,
                       @Parameter(description = Description.PROFILE_IDENTIFIER, example = Example.IDENTIFIER) String profileId);
}
