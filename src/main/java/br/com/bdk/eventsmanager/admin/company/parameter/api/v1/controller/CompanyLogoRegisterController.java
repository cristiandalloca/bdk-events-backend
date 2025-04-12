package br.com.bdk.eventsmanager.admin.company.parameter.api.v1.controller;

import br.com.bdk.eventsmanager.admin.company.domain.validator.CompanyAccessService;
import br.com.bdk.eventsmanager.admin.company.parameter.api.v1.assembler.CompanyLogoModelAssembler;
import br.com.bdk.eventsmanager.admin.company.parameter.api.v1.controller.openapi.CompanyLogoRegisterControllerOpenApi;
import br.com.bdk.eventsmanager.admin.company.parameter.api.v1.model.CompanyLogoModel;
import br.com.bdk.eventsmanager.admin.company.parameter.api.v1.model.input.CompanyLogoInput;
import br.com.bdk.eventsmanager.admin.company.parameter.domain.service.CompanyLogoRegisterService;
import br.com.bdk.eventsmanager.auth.CheckSecurity;
import jakarta.validation.Valid;
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

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/companies/{id}/parameters/logo")
public class CompanyLogoRegisterController implements CompanyLogoRegisterControllerOpenApi {

    private final CompanyLogoRegisterService companyLogoRegisterService;
    private final CompanyLogoModelAssembler companyLogoModelAssembler;
    private final CompanyAccessService companyAccessService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CompanyLogoModel findLogoByExternalCompanyId(@PathVariable("id") String companyExternalId) {
        companyAccessService.validate(companyExternalId);
        var logoURL = companyLogoRegisterService.findLogoByCompanyExternalId(companyExternalId);
        return companyLogoModelAssembler.toModel(logoURL);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @CheckSecurity.Company.CanUpdate
    public void saveLogoByExternalCompanyId(@PathVariable("id") String companyExternalId, @Valid CompanyLogoInput input) {
        companyAccessService.validate(companyExternalId);
        companyLogoRegisterService.saveLogo(companyExternalId, input.getLogo());
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    @CheckSecurity.Company.CanUpdate
    public void removeLogoByExternalCompanyId(@PathVariable("id") String companyExternalId) {
        companyAccessService.validate(companyExternalId);
        companyLogoRegisterService.removeLogoByExternalCompanyId(companyExternalId);
    }


}
