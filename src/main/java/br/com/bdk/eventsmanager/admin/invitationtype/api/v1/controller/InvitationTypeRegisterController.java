package br.com.bdk.eventsmanager.admin.invitationtype.api.v1.controller;

import br.com.bdk.eventsmanager.admin.invitationtype.api.v1.assembler.InvitationTypeModelAssembler;
import br.com.bdk.eventsmanager.admin.invitationtype.api.v1.controller.openapi.InvitationTypeRegisterControllerOpenApi;
import br.com.bdk.eventsmanager.admin.invitationtype.api.v1.disassembler.InvitationTypeInputDisassembler;
import br.com.bdk.eventsmanager.admin.invitationtype.api.v1.model.InvitationTypeModel;
import br.com.bdk.eventsmanager.admin.invitationtype.api.v1.model.input.InvitationTypeInput;
import br.com.bdk.eventsmanager.admin.invitationtype.domain.service.InvitationTypeRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/invitation-types")
public class InvitationTypeRegisterController implements InvitationTypeRegisterControllerOpenApi {

    private final InvitationTypeInputDisassembler disassembler;
    private final InvitationTypeModelAssembler assembler;
    private final InvitationTypeRegistrationService service;

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public InvitationTypeModel create(@RequestBody @Valid InvitationTypeInput input) {
        var newInvitationType = service.validateAndSave(disassembler.toEntity(input));
        return assembler.toModel(newInvitationType);
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public InvitationTypeModel findByExternalId(@PathVariable(name = "id") String externalId) {
        return assembler.toModel(service.findByExternalId(externalId));
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public InvitationTypeModel updateByExternalId(@PathVariable(name = "id") String externalId, @RequestBody @Valid InvitationTypeInput input) {
        var invitationTypeToUpdate = service.findByExternalId(externalId);
        disassembler.copyToEntity(input, invitationTypeToUpdate);
        return assembler.toModel(service.validateAndSave(invitationTypeToUpdate));
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeByExternalId(@PathVariable(name = "id") String externalId) {
        service.removeByExternalId(externalId);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Collection<InvitationTypeModel> findAll(@RequestParam(value = "name", required = false) String name) {
        return assembler.toCollectionModel(service.findAllByName(name));
    }

}
