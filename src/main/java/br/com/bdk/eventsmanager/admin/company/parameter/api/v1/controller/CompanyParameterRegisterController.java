package br.com.bdk.eventsmanager.admin.company.parameter.api.v1.controller;

import br.com.bdk.eventsmanager.admin.company.domain.validator.CompanyAccessService;
import br.com.bdk.eventsmanager.admin.company.parameter.api.v1.assembler.CompanyParameterModelAssembler;
import br.com.bdk.eventsmanager.admin.company.parameter.api.v1.controller.openapi.CompanyParameterRegisterControllerOpenApi;
import br.com.bdk.eventsmanager.admin.company.parameter.api.v1.disassembler.CompanyParameterInputDisassembler;
import br.com.bdk.eventsmanager.admin.company.parameter.api.v1.model.CompanyParameterModel;
import br.com.bdk.eventsmanager.admin.company.parameter.api.v1.model.input.CompanyParameterInput;
import br.com.bdk.eventsmanager.admin.company.parameter.domain.service.CompanyParameterRegisterService;
import br.com.bdk.eventsmanager.auth.CheckSecurity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/v1/companies/{id}/parameters")
public class CompanyParameterRegisterController implements CompanyParameterRegisterControllerOpenApi {

    private final CompanyParameterRegisterService companyParameterRegisterService;
    private final CompanyParameterModelAssembler companyParameterModelAssembler;
    private final CompanyParameterInputDisassembler companyParameterInputDisassembler;
    private final CompanyAccessService companyAccessService;

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CompanyParameterModel findByExternalCompanyId(@PathVariable("id") String companyExternalId) {
        return companyParameterModelAssembler.toModel(companyParameterRegisterService.findByCompanyExternalIdOrCreate(companyExternalId));
    }

    @Override
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Company.CanUpdate
    public CompanyParameterModel updateByExternalCompanyId(@PathVariable("id") String companyExternalId, @RequestBody @Valid CompanyParameterInput input) {
        var companyParameterToUpdate = companyParameterRegisterService.findByCompanyExternalIdOrCreate(companyExternalId);
        companyParameterInputDisassembler.copyToEntity(input, companyParameterToUpdate);
        companyAccessService.validate(companyExternalId);
        return companyParameterModelAssembler.toModel(companyParameterRegisterService.save(companyParameterToUpdate));
    }

}
