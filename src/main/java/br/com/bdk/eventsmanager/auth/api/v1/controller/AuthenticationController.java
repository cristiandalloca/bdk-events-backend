package br.com.bdk.eventsmanager.auth.api.v1.controller;

import br.com.bdk.eventsmanager.auth.api.v1.assembler.TokenModelAssembler;
import br.com.bdk.eventsmanager.auth.api.v1.controller.openapi.AuthenticationControllerOpenApi;
import br.com.bdk.eventsmanager.auth.api.v1.model.TokenModel;
import br.com.bdk.eventsmanager.auth.api.v1.model.input.AuthenticationInput;
import br.com.bdk.eventsmanager.auth.domain.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/auth/token", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationControllerOpenApi {

    private final AuthenticationService authenticationService;
    private final TokenModelAssembler tokenModelAssembler;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public TokenModel getToken(@RequestBody @Valid final AuthenticationInput input) {
        return tokenModelAssembler.toModel(authenticationService.authenticate(input.getUsername(), input.getPassword()));
    }

}
