package br.com.bdk.eventsmanager.admin.company.api.v1.model.input;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import br.com.bdk.eventsmanager.core.util.RegexConstants;
import br.com.bdk.eventsmanager.core.validator.Document;
import br.com.bdk.eventsmanager.core.validator.OnlyNumbers;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CompanyInput {

    @NotBlank
    @Size(max = 255)
    @Schema(example = Example.COMPANY_NAME, description = Description.COMPANY_NAME)
    private String name;

    @NotBlank
    @Size(max = 255)
    @Schema(example = Example.COMPANY_BUSINESS_NAME, description = Description.COMPANY_BUSINESS_NAME)
    private String businessName;

    @NotBlank
    @Document
    @Schema(example = Example.COMPANY_DOCUMENT, description = Description.COMPANY_DOCUMENT)
    private String document;

    @OnlyNumbers
    @Size(max = 14)
    @Schema(example = Example.COMPANY_STATE_REGISTRATION_NUMBER, description = Description.COMPANY_STATE_REGISTRATION_NUMBER)
    private String stateRegistrationNumber;

    @OnlyNumbers
    @Size(max = 11)
    @Schema(example = Example.COMPANY_CITY_REGISTRATION_NUMBER, description = Description.COMPANY_CITY_REGISTRATION_NUMBER)
    private String cityRegistrationNumber;

    @Email(regexp = RegexConstants.EMAIL_REGEX)
    @Size(max = 124)
    @Schema(example = Example.EMAIL, description = Description.COMPANY_EMAIL)
    private String email;

    @OnlyNumbers
    @Size(max = 11)
    @Schema(example = Example.PHONE_NUMBER, description = Description.COMPANY_PHONE_NUMBER)
    private String phoneNumber;

    @NotBlank
    @OnlyNumbers
    @Size(min = 8, max = 8)
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

    @NotNull
    @Schema(example = Example.BOOLEAN, description = Description.COMPANY_ACTIVE)
    private Boolean active;

}
