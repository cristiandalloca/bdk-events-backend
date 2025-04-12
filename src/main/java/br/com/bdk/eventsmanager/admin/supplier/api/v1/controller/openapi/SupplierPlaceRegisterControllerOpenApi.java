package br.com.bdk.eventsmanager.admin.supplier.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.admin.supplier.api.v1.model.SupplierPlaceModel;
import br.com.bdk.eventsmanager.admin.supplier.api.v1.model.input.SupplierPlaceInput;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Collection;

@Tag(name = "Suppliers")
@SecurityRequirement(name = "bearerAuth")
public interface SupplierPlaceRegisterControllerOpenApi {

    @Operation(summary = "Sarch supplier places")
    Collection<SupplierPlaceModel> findAllSupplierPlaces(String supplierExternalId,
                                                         @Parameter(description = Description.SUPPLIER_PLACE_NAME) String name,
                                                         @Parameter(description = Description.SUPPLIER_PLACE_CAPACITY) Integer peopleCapacity,
                                                         @Parameter(description = Description.SUPPLIER_PLACE_ACTIVE) Boolean active);

    @Operation(summary = "Add a supplier place")
    SupplierPlaceModel createSupplierPlace(String supplierExternalId, SupplierPlaceInput input);

    @Operation(summary = "Find supplier place by id")
    SupplierPlaceModel findSupplierPlaceByExternalId(String supplierExternalId, String supplierPlaceExternalId);

    @Operation(summary = "Update a supplier place")
    SupplierPlaceModel updateSupplierPlace(String supplierExternalId, String supplierPlaceExternalId, SupplierPlaceInput input);

    @Operation(summary = "Remove a supplier place")
    void removeSupplierPlaceByExternalId(String supplierExternalId, String supplierPlaceExternalId);

}