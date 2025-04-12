package br.com.bdk.eventsmanager.auth.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.auth.api.v1.model.TokenModel;
import br.com.bdk.eventsmanager.auth.api.v1.model.input.AuthenticationInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication")
public interface AuthenticationControllerOpenApi {

    @Operation(summary = "Get a token")
    TokenModel getToken(AuthenticationInput input);

}
