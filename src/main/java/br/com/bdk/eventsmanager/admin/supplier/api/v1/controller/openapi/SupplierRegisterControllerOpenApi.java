package br.com.bdk.eventsmanager.admin.supplier.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.admin.supplier.api.v1.model.SupplierModel;
import br.com.bdk.eventsmanager.admin.supplier.api.v1.model.input.SupplierInput;
import br.com.bdk.eventsmanager.core.api.model.PageModel;
import br.com.bdk.eventsmanager.core.api.openapi.ParameterQueryPageable;
import br.com.bdk.eventsmanager.core.api.openapi.RegisterController;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;

@Tag(name = "Suppliers")
@SecurityRequirement(name = "bearerAuth")
public interface SupplierRegisterControllerOpenApi extends RegisterController<SupplierInput, SupplierModel> {

    @Override
    @Operation(summary = "Create a supplier")
    SupplierModel create(SupplierInput input);

    @Override
    @Operation(summary = "Find a supplier by id")
    SupplierModel findByExternalId(String externalId);

    @Override
    @Operation(summary = "Update a supplier by id")
    SupplierModel updateByExternalId(String externalId, SupplierInput input);

    @Override
    @Operation(summary = "Remove a supplier by id")
    void removeByExternalId(String externalId);

    @Operation(summary = "Search a supplier")
    @ParameterQueryPageable
    PageModel<SupplierModel> findAll(@Parameter(description = Description.COMPANY_IDENTIFIER) String companyExternalId,
                                     @Parameter(description = Description.SUPPLIER_NAME) String name,
                                     @Parameter(description = Description.CITY_IDENTIFIER) String cityExternalId,
                                     @Parameter(description = Description.SUPPLIER_ACTIVE) Boolean active,
                                     @Parameter(description = Description.SUPPLIER_HAS_EVENTS_PLACES) Boolean hasEventPlaces,
                                     @Parameter(hidden = true) Pageable pageable);
}