package br.com.bdk.eventsmanager.admin.supplier.api.v1.model.input;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import br.com.bdk.eventsmanager.core.validator.OnlyNumbers;
import br.com.bdk.eventsmanager.core.validator.PhoneNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SupplierInput {

    @NotBlank
    @Size(max = 36)
    @Schema(example = Example.IDENTIFIER, description = Description.COMPANY_IDENTIFIER)
    private String companyId;

    @NotBlank
    @Size(max = 255)
    @Schema(example = Example.SUPPLIER_NAME, description = Description.SUPPLIER_NAME)
    private String name;

    @PhoneNumber
    @Size(max = 11)
    @Schema(example = Example.PHONE_NUMBER, description = Description.SUPPLIER_PHONE_NUMBER)
    private String phoneNumber;

    @PhoneNumber
    @Size(max = 11)
    @Schema(example = Example.PHONE_NUMBER, description = Description.SUPPLIER_CELL_PHONE_NUMBER)
    private String cellPhoneNumber;

    @NotBlank
    @OnlyNumbers
    @Size(max = 8)
    @Schema(example = Example.ADDRESS_POSTAL_CODE, description = Description.ADDRESS_POSTAL_CODE)
    private String postalCode;

    @NotBlank
    @Size(max = 255)
    @Schema(example = Example.ADDRESS_STREET, description = Description.ADDRESS_STREET)
    private String street;

    @Size(max = 12)
    @Schema(example = Example.ADDRESS_STREET_NUMBER, description = Description.ADDRESS_STREET_NUMBER)
    private String streetNumber;

    @NotBlank
    @Size(max = 255)
    @Schema(example = Example.ADDRESS_NEIGHBORHOOD, description = Description.ADDRESS_NEIGHBORHOOD)
    private String neighborhood;

    @Size(max = 50)
    @Schema(example = Example.ADDRESS_COMPLEMENT, description = Description.ADDRESS_COMPLEMENT)
    private String complement;

    @NotBlank
    @Size(max = 36)
    @Schema(example = Example.IDENTIFIER, description = Description.CITY_IDENTIFIER)
    private String cityId;

    @Email
    @Size(max = 124)
    @Schema(example = Example.EMAIL, description = Description.SUPPLIER_EMAIL)
    private String email;

    @NotNull
    @Schema(example = Example.BOOLEAN, description = Description.SUPPLIER_HAS_EVENTS_PLACES)
    private Boolean hasEventsPlaces;

    @NotNull
    @Schema(example = Example.BOOLEAN, description = Description.SUPPLIER_ACTIVE)
    private Boolean active;

}
