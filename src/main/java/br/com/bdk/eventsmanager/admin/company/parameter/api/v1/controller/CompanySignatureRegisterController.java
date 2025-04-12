package br.com.bdk.eventsmanager.admin.company.parameter.api.v1.controller;

import br.com.bdk.eventsmanager.admin.company.domain.validator.CompanyAccessService;
import br.com.bdk.eventsmanager.admin.company.parameter.api.v1.assembler.CompanySignatureModelAssembler;
import br.com.bdk.eventsmanager.admin.company.parameter.api.v1.controller.openapi.CompanySignatureRegisterControllerOpenApi;
import br.com.bdk.eventsmanager.admin.company.parameter.api.v1.model.CompanySignatureModel;
import br.com.bdk.eventsmanager.admin.company.parameter.api.v1.model.input.CompanySignatureInput;
import br.com.bdk.eventsmanager.admin.company.parameter.domain.service.CompanySignatureRegisterService;
import br.com.bdk.eventsmanager.auth.CheckSecurity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/v1/companies/{id}/parameters/signature")
public class CompanySignatureRegisterController implements CompanySignatureRegisterControllerOpenApi {

    private final CompanySignatureRegisterService companySignatureRegisterService;
    private final CompanySignatureModelAssembler companySignatureModelAssembler;
    private final CompanyAccessService companyAccessService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CompanySignatureModel findSignatureCompany(@PathVariable("id") String companyExternalId) {
        companyAccessService.validate(companyExternalId);
        var signatureURL = companySignatureRegisterService.findSignatureByCompanyExternalId(companyExternalId);
        return companySignatureModelAssembler.toModel(signatureURL);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @CheckSecurity.Company.CanUpdate
    public void insertOrUpdateSignatureCompany(@PathVariable("id") String companyExternalId,
                                               @Valid CompanySignatureInput input) {
        companyAccessService.validate(companyExternalId);
        companySignatureRegisterService.saveSignature(companyExternalId, input.getSignature());
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    @CheckSecurity.Company.CanUpdate
    public void removeSignatureCompany(@PathVariable("id") String companyExternalId) {
        companyAccessService.validate(companyExternalId);
        companySignatureRegisterService.removeSignatureByCompanyExternalId(companyExternalId);
    }

}
