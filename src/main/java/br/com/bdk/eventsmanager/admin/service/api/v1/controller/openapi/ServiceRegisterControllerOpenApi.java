package br.com.bdk.eventsmanager.admin.service.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.admin.service.api.v1.model.ServiceModel;
import br.com.bdk.eventsmanager.admin.service.api.v1.model.ServiceSupplierModel;
import br.com.bdk.eventsmanager.admin.service.api.v1.model.input.ServiceInput;
import br.com.bdk.eventsmanager.admin.service.api.v1.model.input.ServiceSupplierInput;
import br.com.bdk.eventsmanager.admin.service.domain.model.ServiceCostType;
import br.com.bdk.eventsmanager.core.api.model.PageModel;
import br.com.bdk.eventsmanager.core.api.openapi.ParameterQueryPageable;
import br.com.bdk.eventsmanager.core.api.openapi.RegisterController;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

@Tag(name = "Services")
@SecurityRequirement(name = "bearerAuth")
public interface ServiceRegisterControllerOpenApi extends RegisterController<ServiceInput, ServiceModel> {

    @Override
    @Operation(summary = "Create a service")
    ServiceModel create(ServiceInput input);

    @Override
    @Operation(summary = "Find a service by id")
    ServiceModel findByExternalId(@Parameter(description = Description.SERVICE_IDENTIFIER, example = Example.IDENTIFIER) String externalId);

    @Override
    @Operation(summary = "Update a service by id ")
    ServiceModel updateByExternalId(@Parameter(description = Description.SERVICE_IDENTIFIER, example = Example.IDENTIFIER) String externalId, ServiceInput input);

    @Override
    @Operation(summary = "Remove a service by id")
    void removeByExternalId(String externalId);

    @Operation(summary = "Search services")
    @ParameterQueryPageable
    PageModel<ServiceModel> findAll(@Parameter(description = Description.SERVICE_NAME) String name,
                                  @Parameter(description = Description.SERVICE_COST_TYPE) ServiceCostType costType,
                                  @Parameter(description = Description.SERVICE_APPLY_BDI) Boolean applyBDI,
                                  @Parameter(description = Description.SERVICE_APPLY_SELLER_COMMISSION) Boolean applySellerCommission,
                                  @Parameter(description = Description.SERVICE_ACTIVE) Boolean active,
                                  @Parameter(description = Description.COMPANY_IDENTIFIER) String companyExternalId,
                                  @Parameter(description = Description.EVENT_IDENTIFIER) String eventExternalIdToIgnore,
                                  @Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Get suppliers")
    Collection<ServiceSupplierModel> getSuppliers(@Parameter(description = Description.SERVICE_IDENTIFIER, example = Example.IDENTIFIER) String externalId);

    @Operation(summary = "Add supliers")
    void addSuppliers(@Parameter(description = Description.SERVICE_IDENTIFIER, example = Example.IDENTIFIER) String externalId,
                      ServiceSupplierInput input);

    @Operation(summary = "Remove a supplier")
    void removeSupplier(@Parameter(description = Description.SERVICE_IDENTIFIER, example = Example.IDENTIFIER) String externalId, String serviceSupplierId);
}
