package br.com.bdk.eventsmanager.admin.privilege.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.admin.privilege.api.v1.model.PrivilegeModel;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Collection;

@Tag(name = "Privileges")
@SecurityRequirement(name = "bearerAuth")
public interface PrivilegeRegisterControllerOpenApi {

    @Operation(summary = "Find all privileges")
    Collection<PrivilegeModel> findAll(@Parameter(description = Description.PRIVILEGE_DESCRIPTION) String description);

}
