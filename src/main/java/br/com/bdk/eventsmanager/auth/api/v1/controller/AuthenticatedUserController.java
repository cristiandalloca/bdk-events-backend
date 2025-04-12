package br.com.bdk.eventsmanager.auth.api.v1.controller;

import br.com.bdk.eventsmanager.auth.api.v1.assembler.AuthenticatedUserModelAssembler;
import br.com.bdk.eventsmanager.auth.api.v1.controller.openapi.AuthenticatedUserControllerOpenApi;
import br.com.bdk.eventsmanager.auth.api.v1.model.AuthenticatedUserModel;
import br.com.bdk.eventsmanager.auth.domain.model.UserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/auth/token", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthenticatedUserController implements AuthenticatedUserControllerOpenApi {

    private final AuthenticatedUserModelAssembler authenticatedUserModelAssembler;

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public AuthenticatedUserModel getInfo() {
        final var userDetails = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authenticatedUserModelAssembler.toModel(userDetails);
    }
}
