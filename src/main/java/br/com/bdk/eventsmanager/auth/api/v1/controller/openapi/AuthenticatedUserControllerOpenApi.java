package br.com.bdk.eventsmanager.auth.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.auth.api.v1.model.AuthenticatedUserModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication")
@SecurityRequirement(name = "bearerAuth")
public interface AuthenticatedUserControllerOpenApi {

    @Operation(summary = "Get info authenticaded user")
    AuthenticatedUserModel getInfo();

}