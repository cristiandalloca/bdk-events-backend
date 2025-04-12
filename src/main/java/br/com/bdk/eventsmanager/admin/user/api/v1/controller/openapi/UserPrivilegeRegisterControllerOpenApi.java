package br.com.bdk.eventsmanager.admin.user.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.admin.user.api.v1.model.UserPrivilegeModel;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Collection;

@Tag(name = "Users")
@SecurityRequirement(name = "bearerAuth")
public interface UserPrivilegeRegisterControllerOpenApi {

    @Operation(summary = "Find user privileges")
    Collection<UserPrivilegeModel> findAllPrivileges(@Parameter(description = Description.USER_IDENTIFIER, example = Example.IDENTIFIER) String userExternalId);
}
