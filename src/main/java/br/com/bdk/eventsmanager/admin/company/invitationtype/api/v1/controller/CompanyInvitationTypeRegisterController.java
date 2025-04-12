package br.com.bdk.eventsmanager.admin.company.invitationtype.api.v1.controller;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.company.domain.validator.CompanyAccessService;
import br.com.bdk.eventsmanager.admin.company.invitationtype.api.v1.assembler.CompanyInvitationTypeModelAssembler;
import br.com.bdk.eventsmanager.admin.company.invitationtype.api.v1.controller.openapi.CompanyInvitationTypeRegisterControllerOpenApi;
import br.com.bdk.eventsmanager.admin.company.invitationtype.api.v1.model.CompanyInvitationTypeModel;
import br.com.bdk.eventsmanager.admin.company.invitationtype.domain.service.CompanyInvitationTypeRegisterService;
import br.com.bdk.eventsmanager.admin.invitationtype.domain.model.InvitationType;
import br.com.bdk.eventsmanager.auth.CheckSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/companies/{id}/parameters/invitation-types")
public class CompanyInvitationTypeRegisterController implements CompanyInvitationTypeRegisterControllerOpenApi {

    private final CompanyInvitationTypeRegisterService companyInvitationTypeRegisterService;
    private final CompanyInvitationTypeModelAssembler companyInvitationTypeModelAssembler;
    private final CompanyAccessService companyAccessService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{invitationTypeId}")
    @CheckSecurity.Company.CanUpdate
    public void addInvitationType(@PathVariable("id") String companyExternalId, @PathVariable("invitationTypeId") String invitationTypeExternalId) {
        companyAccessService.validate(companyExternalId);
        companyInvitationTypeRegisterService.addInvitationType(new Company(companyExternalId), new InvitationType(invitationTypeExternalId));
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{invitationTypeId}")
    @CheckSecurity.Company.CanUpdate
    public void removeInvitationType(@PathVariable("id") String companyExternalId, @PathVariable("invitationTypeId") String invitationTypeExternalId) {
        companyAccessService.validate(companyExternalId);
        companyInvitationTypeRegisterService.removeInvitationType(new Company(companyExternalId), new InvitationType(invitationTypeExternalId));
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<CompanyInvitationTypeModel> findAllInvitationTypes(@PathVariable("id") String companyExternalId) {
        companyAccessService.validate(companyExternalId);
        return companyInvitationTypeModelAssembler.toCollectionModel(companyInvitationTypeRegisterService.findAllInvitationTypesByCompanyExternalId(companyExternalId));
    }

}
