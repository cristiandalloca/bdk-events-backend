package br.com.bdk.eventsmanager.admin.user.api.v1.controller;

import br.com.bdk.eventsmanager.admin.user.api.v1.assembler.UserPrivilegeModelAssembler;
import br.com.bdk.eventsmanager.admin.user.api.v1.controller.openapi.UserPrivilegeRegisterControllerOpenApi;
import br.com.bdk.eventsmanager.admin.user.api.v1.model.UserPrivilegeModel;
import br.com.bdk.eventsmanager.admin.user.domain.service.UserPrivilegeRegisterService;
import br.com.bdk.eventsmanager.auth.CheckSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/users/{id}/privileges")
public class UserPrivilegeRegisterController implements UserPrivilegeRegisterControllerOpenApi {

    private final UserPrivilegeRegisterService userPrivilegeRegisterService;
    private final UserPrivilegeModelAssembler userPrivilegeModelAssembler;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.User.CanRead
    @ResponseStatus(HttpStatus.OK)
    public Collection<UserPrivilegeModel> findAllPrivileges(@PathVariable(name = "id") String userExternalId) {
        return userPrivilegeModelAssembler.toCollectionModel(userPrivilegeRegisterService.findAllPrivileges(userExternalId));
    }
}
