package br.com.bdk.eventsmanager.admin.address.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.admin.address.api.v1.model.AddressModel;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import br.com.bdk.eventsmanager.core.validator.ExactSize;
import br.com.bdk.eventsmanager.core.validator.OnlyNumbers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
@Tag(name = "Adresses")
@SecurityRequirement(name = "bearerAuth")
public interface AddressSearchControllerOpenApi {

    @Operation(summary = "Find address by postal code")
    @Parameter(name = "postalCode", description = Description.ADDRESS_POSTAL_CODE, example = Example.ADDRESS_POSTAL_CODE)
    AddressModel findByPostalCode(@NotBlank @ExactSize(size = 8) @OnlyNumbers String postalCode);
}
