package br.com.bdk.eventsmanager.admin.service.api.v1.controller;

import br.com.bdk.eventsmanager.admin.company.domain.validator.CompanyAccessService;
import br.com.bdk.eventsmanager.admin.service.api.v1.assembler.ServiceModelAssembler;
import br.com.bdk.eventsmanager.admin.service.api.v1.assembler.ServiceSupplierModelAssembler;
import br.com.bdk.eventsmanager.admin.service.api.v1.controller.openapi.ServiceRegisterControllerOpenApi;
import br.com.bdk.eventsmanager.admin.service.api.v1.disassembler.ServiceInputDisassembler;
import br.com.bdk.eventsmanager.admin.service.api.v1.disassembler.ServiceSupplierInputDisassembler;
import br.com.bdk.eventsmanager.admin.service.api.v1.model.ServiceModel;
import br.com.bdk.eventsmanager.admin.service.api.v1.model.ServiceSupplierModel;
import br.com.bdk.eventsmanager.admin.service.api.v1.model.input.ServiceInput;
import br.com.bdk.eventsmanager.admin.service.api.v1.model.input.ServiceSupplierInput;
import br.com.bdk.eventsmanager.admin.service.domain.model.ServiceCostType;
import br.com.bdk.eventsmanager.admin.service.domain.model.dto.ServiceFilter;
import br.com.bdk.eventsmanager.admin.service.domain.service.ServiceRegistrationService;
import br.com.bdk.eventsmanager.admin.service.domain.service.ServiceSupplierRegisterService;
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

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/services")
public class ServiceRegisterController implements ServiceRegisterControllerOpenApi {

    private final ServiceRegistrationService service;
    private final ServiceInputDisassembler disassembler;
    private final ServiceModelAssembler assembler;
    private final ServiceSupplierRegisterService serviceSupplierRegisterService;
    private final ServiceSupplierModelAssembler serviceSupplierModelAssembler;
    private final ServiceSupplierInputDisassembler serviceSupplierInputDisassembler;
    private final CompanyAccessService companyAccessService;

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Service.CanCreate
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceModel create(@RequestBody @Valid ServiceInput input) {
        return assembler.toModel(service.validateAndSave(disassembler.toEntity(input)));
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Service.CanRead
    @ResponseStatus(HttpStatus.OK)
    public ServiceModel findByExternalId(@PathVariable(name = "id") String externalId) {
        return assembler.toModel(service.findByExternalId(externalId));
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Service.CanUpdate
    @ResponseStatus(HttpStatus.OK)
    public ServiceModel updateByExternalId(@PathVariable(name = "id") String externalId, @RequestBody @Valid ServiceInput input) {
        var serviceToUpdate = service.findByExternalId(externalId);
        disassembler.copyToEntity(input, serviceToUpdate);
        return assembler.toModel(service.validateAndSave(serviceToUpdate));
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @CheckSecurity.Service.CanDelete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeByExternalId(@PathVariable(name = "id") String externalId) {
        service.removeByExternalId(externalId);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Service.CanRead
    @ResponseStatus(HttpStatus.OK)
    public PageModel<ServiceModel> findAll(@RequestParam(value = "name", required = false) String name,
                                         @RequestParam(value = "costType", required = false) ServiceCostType costType,
                                         @RequestParam(value = "applyBDI", required = false) Boolean applyBDI,
                                         @RequestParam(value = "applySellerCommission", required = false) Boolean applySellerCommission,
                                         @RequestParam(value = "active", required = false) Boolean active,
                                         @RequestParam(value = "companyId", required = false) String companyExternalId,
                                         @RequestParam(value = "eventIdToIgnore", required = false) String eventExternalIdToIgnore,
                                         Pageable pageable) {
        final var filter = ServiceFilter.builder()
                .name(name)
                .costType(costType)
                .active(active)
                .applyBDI(applyBDI)
                .applySellerCommission(applySellerCommission)
                .companyExternalId(companyExternalId)
                .eventExternalIdToIgnore(eventExternalIdToIgnore)
                .build();
        companyAccessService.validate(companyExternalId);
        return assembler.toPageModel(service.findAllByFilters(filter, pageable));
    }

    @Override
    @GetMapping(path = "/{id}/suppliers", produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Service.CanRead
    @ResponseStatus(HttpStatus.OK)
    public Collection<ServiceSupplierModel> getSuppliers(@PathVariable("id") String externalId) {
        return serviceSupplierModelAssembler.toCollectionModel(serviceSupplierRegisterService.findAllSuppliersByServiceExternalId(externalId));
    }

    @Override
    @PostMapping(path = "/{id}/suppliers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Service.CanUpdate
    @ResponseStatus(HttpStatus.CREATED)
    public void addSuppliers(@PathVariable("id") String externalId, @RequestBody @Valid ServiceSupplierInput input) {
        serviceSupplierRegisterService.addSupliersToService(externalId, serviceSupplierInputDisassembler.toEntity(input));
    }

    @Override
    @DeleteMapping(path = "/{id}/suppliers/{serviceSupplierId}")
    @CheckSecurity.Service.CanUpdate
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeSupplier(@PathVariable("id") String externalId, @PathVariable("serviceSupplierId") String serviceSupplierId) {
        serviceSupplierRegisterService.removeServiceSupplierByExternalId(serviceSupplierId);
    }
}
