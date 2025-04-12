package br.com.bdk.eventsmanager.admin.supplier.api.v1.controller;

import br.com.bdk.eventsmanager.admin.supplier.api.v1.assembler.SupplierPlaceModelAssembler;
import br.com.bdk.eventsmanager.admin.supplier.api.v1.controller.openapi.SupplierPlaceRegisterControllerOpenApi;
import br.com.bdk.eventsmanager.admin.supplier.api.v1.disassembler.SupplierPlaceInputDisassembler;
import br.com.bdk.eventsmanager.admin.supplier.api.v1.model.SupplierPlaceModel;
import br.com.bdk.eventsmanager.admin.supplier.api.v1.model.input.SupplierPlaceInput;
import br.com.bdk.eventsmanager.admin.supplier.domain.service.SupplierPlaceRegisterService;
import br.com.bdk.eventsmanager.auth.CheckSecurity;
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
@RequestMapping(path = "/v1/suppliers")
public class SupplierPlaceRegisterController implements SupplierPlaceRegisterControllerOpenApi {

    private final SupplierPlaceModelAssembler supplierPlaceModelAssembler;
    private final SupplierPlaceRegisterService supplierPlaceRegisterService;
    private final SupplierPlaceInputDisassembler supplierPlaceInputDisassembler;

    @Override
    @GetMapping(path = "{id}/places", produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Supplier.CanRead
    @ResponseStatus(HttpStatus.OK)
    public Collection<SupplierPlaceModel> findAllSupplierPlaces(@PathVariable("id") String supplierExternalId,
                                                                @RequestParam(required = false) String name,
                                                                @RequestParam(required = false) Integer peopleCapacity,
                                                                @RequestParam(required = false) Boolean active) {
        return supplierPlaceModelAssembler.toCollectionModel(supplierPlaceRegisterService.findAll(supplierExternalId, name, peopleCapacity, active));
    }

    @Override
    @PostMapping(path = "/{id}/places", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Supplier.CanUpdate
    @ResponseStatus(HttpStatus.CREATED)
    public SupplierPlaceModel createSupplierPlace(@PathVariable("id") String supplierExternalId,
                                                  @RequestBody @Valid SupplierPlaceInput input) {
        final var newSupplierPlace = supplierPlaceInputDisassembler.toEntity(input);
        return supplierPlaceModelAssembler.toModel(supplierPlaceRegisterService.saveAndValidate(supplierExternalId, newSupplierPlace));
    }

    @Override
    @GetMapping(path = "/{id}/places/{placeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Supplier.CanRead
    @ResponseStatus(HttpStatus.OK)
    public SupplierPlaceModel findSupplierPlaceByExternalId(@PathVariable("id") String supplierExternalId,
                                                            @PathVariable("placeId") String supplierPlaceExternalId) {
        return supplierPlaceModelAssembler.toModel(supplierPlaceRegisterService.findByExternalId(supplierExternalId, supplierPlaceExternalId));
    }

    @Override
    @PutMapping(path = "/{id}/places/{placeId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Supplier.CanUpdate
    @ResponseStatus(HttpStatus.OK)
    public SupplierPlaceModel updateSupplierPlace(@PathVariable("id") String supplierExternalId,
                                                  @PathVariable("placeId") String supplierPlaceExternalId,
                                                  @RequestBody @Valid SupplierPlaceInput input) {
        var supplierPlaceToUpdate = supplierPlaceRegisterService.findByExternalId(supplierExternalId, supplierPlaceExternalId);
        supplierPlaceInputDisassembler.copyToEntity(input, supplierPlaceToUpdate);
        return supplierPlaceModelAssembler.toModel(supplierPlaceRegisterService.saveAndValidate(supplierExternalId, supplierPlaceToUpdate));

    }

    @Override
    @DeleteMapping(path = "/{id}/places/{placeId}")
    @CheckSecurity.Supplier.CanDelete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeSupplierPlaceByExternalId(@PathVariable("id") String supplierExternalId,
                                                @PathVariable("placeId") String supplierPlaceExternalId) {
        supplierPlaceRegisterService.removeByExternalId(supplierExternalId, supplierPlaceExternalId);
    }

}
