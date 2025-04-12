package br.com.bdk.eventsmanager.admin.privilege.api.v1.controller;


import br.com.bdk.eventsmanager.admin.privilege.api.v1.assembler.PrivilegeModelAssembler;
import br.com.bdk.eventsmanager.admin.privilege.api.v1.controller.openapi.PrivilegeRegisterControllerOpenApi;
import br.com.bdk.eventsmanager.admin.privilege.api.v1.model.PrivilegeModel;
import br.com.bdk.eventsmanager.admin.privilege.domain.service.PrivilegeRegistrationService;
import br.com.bdk.eventsmanager.auth.CheckSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/privileges")
public class PrivilegeRegisterController implements PrivilegeRegisterControllerOpenApi {

    private final PrivilegeRegistrationService privilegeRegisterService;
    private final PrivilegeModelAssembler privilegeModelAssembler;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Profile.CanRead
    @ResponseStatus(HttpStatus.OK)
    public Collection<PrivilegeModel> findAll(@RequestParam(value = "description", required = false) String description) {
        return privilegeModelAssembler.toCollectionModel(privilegeRegisterService.findAll(description));
    }

}
