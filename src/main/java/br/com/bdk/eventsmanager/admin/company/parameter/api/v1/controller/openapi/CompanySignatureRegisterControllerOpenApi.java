package br.com.bdk.eventsmanager.admin.company.parameter.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.admin.company.parameter.api.v1.model.CompanySignatureModel;
import br.com.bdk.eventsmanager.admin.company.parameter.api.v1.model.input.CompanySignatureInput;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;

@Tag(name = "Companies")
@SecurityRequirement(name = "bearerAuth")
public interface CompanySignatureRegisterControllerOpenApi {

    @Operation(summary = "Find a signature by company id")
    CompanySignatureModel findSignatureCompany(@Parameter(description = Description.COMPANY_IDENTIFIER, example = Example.IDENTIFIER) String externalCompanyId);

    @Operation(summary = "Insert or update a signature company", requestBody = @RequestBody(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)))
    void insertOrUpdateSignatureCompany(@Parameter(description = Description.COMPANY_IDENTIFIER, example = Example.IDENTIFIER) String externalCompanyId,
                                        CompanySignatureInput input);

    @Operation(summary = "Remove a signature company")
    void removeSignatureCompany(@Parameter(description = Description.COMPANY_IDENTIFIER, example = Example.IDENTIFIER) String externalCompanyId);
}
