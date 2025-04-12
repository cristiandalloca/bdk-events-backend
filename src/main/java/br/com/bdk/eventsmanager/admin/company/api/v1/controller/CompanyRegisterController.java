package br.com.bdk.eventsmanager.admin.company.api.v1.controller;

import br.com.bdk.eventsmanager.admin.company.api.v1.assembler.CompanyModelAssembler;
import br.com.bdk.eventsmanager.admin.company.api.v1.controller.openapi.CompanyRegisterControllerOpenApi;
import br.com.bdk.eventsmanager.admin.company.api.v1.disassembler.CompanyInputDisassembler;
import br.com.bdk.eventsmanager.admin.company.api.v1.model.CompanyModel;
import br.com.bdk.eventsmanager.admin.company.api.v1.model.input.CompanyInput;
import br.com.bdk.eventsmanager.admin.company.domain.service.CompanyRegistrationService;
import br.com.bdk.eventsmanager.auth.CheckSecurity;
import br.com.bdk.eventsmanager.core.api.model.PageModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/companies")
public class CompanyRegisterController implements CompanyRegisterControllerOpenApi {

    private final CompanyRegistrationService companyRegistrationService;
    private final CompanyInputDisassembler companyInputDisassembler;
    private final CompanyModelAssembler companyModelAssembler;

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @CheckSecurity.Company.CanRead
    public CompanyModel findByExternalId(@PathVariable(name = "id") String externalId) {
        return companyModelAssembler.toModel(companyRegistrationService.findByExternalId(externalId));
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Company.CanCreate
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyModel create(@RequestBody @Valid CompanyInput input) {
        var newCompany = companyRegistrationService.validateAndSave(companyInputDisassembler.toEntity(input));
        return companyModelAssembler.toModel(newCompany);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @CheckSecurity.Company.CanUpdate
    public CompanyModel updateByExternalId(@PathVariable(name = "id") String externalId, @RequestBody @Valid CompanyInput input) {
        var companyToUpdate = companyRegistrationService.findByExternalId(externalId);
        companyInputDisassembler.copyToEntity(input, companyToUpdate);
        return companyModelAssembler.toModel(companyRegistrationService.validateAndSave(companyToUpdate));
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Company.CanRead
    @ResponseStatus(HttpStatus.OK)
    public PageModel<CompanyModel> findAll(@RequestParam(value = "name", required = false) String name, Pageable pageable) {
        return companyModelAssembler.toPageModel(companyRegistrationService.findAll(name, pageable));
    }

    @Override
    @DeleteMapping("/{id}")
    @CheckSecurity.Company.CanDelete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeByExternalId(@PathVariable(name = "id") String externalId) {
        companyRegistrationService.removeByExternalId(externalId);
    }
}
