package br.com.bdk.eventsmanager.admin.company.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.admin.company.api.v1.model.CompanyModel;
import br.com.bdk.eventsmanager.admin.company.api.v1.model.input.CompanyInput;
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

@Tag(name = "Companies")
@SecurityRequirement(name = "bearerAuth")
public interface CompanyRegisterControllerOpenApi extends RegisterController<CompanyInput, CompanyModel> {

    @Operation(summary = "Find a company by id")
    CompanyModel findByExternalId(@Parameter(description = Description.COMPANY_IDENTIFIER, example = Example.IDENTIFIER) String externalId);

    @Operation(summary = "Create a company")
    CompanyModel create(CompanyInput input);

    @Operation(summary = "Update a company")
    CompanyModel updateByExternalId(@Parameter(description = Description.COMPANY_IDENTIFIER, example = Example.IDENTIFIER) String externalId,
                                    CompanyInput input);

    @Operation(summary = "Remove a company by id")
    void removeByExternalId(@Parameter(description = Description.COMPANY_IDENTIFIER, example = Example.IDENTIFIER) String externalId);

    @Operation(summary = "Search companies")
    @ParameterQueryPageable
    PageModel<CompanyModel> findAll(@Parameter(description = Description.COMPANY_NAME) String name,
                                    @Parameter(hidden = true) Pageable pageable);
}
