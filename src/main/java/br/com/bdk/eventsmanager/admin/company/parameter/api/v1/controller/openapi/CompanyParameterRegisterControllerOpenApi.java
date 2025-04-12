package br.com.bdk.eventsmanager.admin.company.parameter.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.admin.company.parameter.api.v1.model.CompanyParameterModel;
import br.com.bdk.eventsmanager.admin.company.parameter.api.v1.model.input.CompanyParameterInput;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Companies")
@SecurityRequirement(name = "bearerAuth")
public interface CompanyParameterRegisterControllerOpenApi {

    @Operation(summary = "Find parameters by company id")
    CompanyParameterModel findByExternalCompanyId(@Parameter(description = Description.COMPANY_IDENTIFIER, example = Example.IDENTIFIER) String externalCompanyId);

    @Operation(summary = "Update parameters company")
    CompanyParameterModel updateByExternalCompanyId(@Parameter(description = Description.COMPANY_IDENTIFIER, example = Example.IDENTIFIER) String externalCompanyId,
                                                    CompanyParameterInput input);

}
