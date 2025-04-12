package br.com.bdk.eventsmanager.admin.supplier.api.v1.controller;

import br.com.bdk.eventsmanager.admin.company.domain.validator.CompanyAccessService;
import br.com.bdk.eventsmanager.admin.supplier.api.v1.assembler.SupplierModelAssembler;
import br.com.bdk.eventsmanager.admin.supplier.api.v1.controller.openapi.SupplierRegisterControllerOpenApi;
import br.com.bdk.eventsmanager.admin.supplier.api.v1.disassembler.SupplierInputDisassembler;
import br.com.bdk.eventsmanager.admin.supplier.api.v1.model.SupplierModel;
import br.com.bdk.eventsmanager.admin.supplier.api.v1.model.input.SupplierInput;
import br.com.bdk.eventsmanager.admin.supplier.domain.model.filter.SupplierFilter;
import br.com.bdk.eventsmanager.admin.supplier.domain.service.SupplierRegistrationService;
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
@RequestMapping(path = "/v1/suppliers")
public class SupplierRegisterController implements SupplierRegisterControllerOpenApi {

    private final SupplierInputDisassembler disassembler;
    private final SupplierModelAssembler assembler;
    private final SupplierRegistrationService service;
    private final CompanyAccessService companyAccessService;

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Supplier.CanCreate
    @ResponseStatus(HttpStatus.CREATED)
    public SupplierModel create(@RequestBody @Valid SupplierInput input) {
        return assembler.toModel(service.validateAndSave(disassembler.toEntity(input)));
    }

    @Override
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Supplier.CanRead
    @ResponseStatus(HttpStatus.OK)
    public SupplierModel findByExternalId(@PathVariable("id") String externalId) {
        return assembler.toModel(service.findByExternalId(externalId));
    }

    @Override
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Supplier.CanUpdate
    @ResponseStatus(HttpStatus.OK)
    public SupplierModel updateByExternalId(@PathVariable("id") String externalId, @RequestBody @Valid SupplierInput input) {
        var supplierToUpdate = service.findByExternalId(externalId);
        disassembler.copyToEntity(input, supplierToUpdate);
        return assembler.toModel(service.validateAndSave(supplierToUpdate));
    }

    @Override
    @DeleteMapping("/{id}")
    @CheckSecurity.Supplier.CanDelete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeByExternalId(@PathVariable("id") String externalId) {
        service.removeByExternalId(externalId);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Supplier.CanRead
    @ResponseStatus(HttpStatus.OK)
    public PageModel<SupplierModel> findAll(@RequestParam(value = "companyId", required = false) String companyExternalId,
                                            @RequestParam(value = "name", required = false) String name,
                                            @RequestParam(value = "cityId", required = false) String cityExternalId,
                                            @RequestParam(value = "active", required = false) Boolean active,
                                            @RequestParam(value = "hasEventsPlaces", required = false) Boolean hasEventsPlaces,
                                            Pageable pageable) {
        final var filter = SupplierFilter.builder()
                .companyExternalId(companyExternalId)
                .name(name)
                .cityExternalId(cityExternalId)
                .active(active)
                .hasEventsPlaces(hasEventsPlaces)
                .build();
        companyAccessService.validate(companyExternalId);
        return assembler.toPageModel(service.findAll(filter, pageable));
    }
}
