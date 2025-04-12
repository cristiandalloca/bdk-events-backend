package br.com.bdk.eventsmanager.admin.company.parameter.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.admin.company.parameter.api.v1.model.CompanyLogoModel;
import br.com.bdk.eventsmanager.admin.company.parameter.api.v1.model.input.CompanyLogoInput;
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
public interface CompanyLogoRegisterControllerOpenApi {

    @Operation(summary = "Find a logo by company id")
    CompanyLogoModel findLogoByExternalCompanyId(@Parameter(description = Description.COMPANY_IDENTIFIER, example = Example.IDENTIFIER) String externalCompanyId);

    @Operation(summary = "Insert or update a logo company", requestBody = @RequestBody(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)))
    void saveLogoByExternalCompanyId(@Parameter(description = Description.COMPANY_IDENTIFIER, example = Example.IDENTIFIER) String externalCompanyId,
                                     CompanyLogoInput input);

    @Operation(summary = "Remove a logo company")
    void removeLogoByExternalCompanyId(@Parameter(description = Description.COMPANY_IDENTIFIER, example = Example.IDENTIFIER) String externalCompanyId);
}
