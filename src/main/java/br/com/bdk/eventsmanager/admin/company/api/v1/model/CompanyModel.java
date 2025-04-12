package br.com.bdk.eventsmanager.admin.company.api.v1.model;

import br.com.bdk.eventsmanager.admin.city.api.v1.model.CityStateModel;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class CompanyModel {

    @Schema(example = Example.IDENTIFIER, description = Description.COMPANY_IDENTIFIER)
    private String id;

    @Schema(example = Example.COMPANY_NAME, description = Description.COMPANY_NAME)
    private String name;

    @Schema(example = Example.COMPANY_BUSINESS_NAME, description = Description.COMPANY_BUSINESS_NAME)
    private String businessName;

    @Schema(example = Example.ADDRESS_POSTAL_CODE, description = Description.ADDRESS_POSTAL_CODE)
    private String postalCode;

    @Schema(example = Example.ADDRESS_STREET, description = Description.ADDRESS_STREET)
    private String street;

    @Schema(examples = Example.ADDRESS_STREET_NUMBER, description = Description.ADDRESS_STREET_NUMBER)
    private String streetNumber;

    @Schema(example = Example.ADDRESS_NEIGHBORHOOD, description = Description.ADDRESS_NEIGHBORHOOD)
    private String neighborhood;

    @Schema(example = Example.ADDRESS_COMPLEMENT, description = Description.ADDRESS_COMPLEMENT)
    private String complement;

    private CityStateModel city;

    @Schema(example = Example.EMAIL, description = Description.COMPANY_EMAIL)
    private String email;

    @Schema(examples = Example.PHONE_NUMBER, description = Description.COMPANY_PHONE_NUMBER)
    private String phoneNumber;

    @Schema(examples = Example.COMPANY_DOCUMENT, description = Description.COMPANY_DOCUMENT)
    private String document;

    @Schema(example = Example.COMPANY_STATE_REGISTRATION_NUMBER, description = Description.COMPANY_STATE_REGISTRATION_NUMBER)
    private String stateRegistrationNumber;

    @Schema(example = Example.COMPANY_CITY_REGISTRATION_NUMBER, description = Description.COMPANY_CITY_REGISTRATION_NUMBER)
    private String cityRegistrationNumber;

    @Schema(example = Example.BOOLEAN, description = Description.COMPANY_ACTIVE)
    private boolean active;

}
