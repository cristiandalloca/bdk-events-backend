package br.com.bdk.eventsmanager.admin.supplier.api.v1.model;

import br.com.bdk.eventsmanager.admin.city.api.v1.model.CityStateModel;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SupplierModel {

    @Schema(example = Example.IDENTIFIER, description = Description.SUPPLIER_IDENTIFIER)
    private String id;

    private SupplierCompanyModel company;

    @Schema(example = Example.SUPPLIER_NAME, description = Description.SUPPLIER_NAME)
    private String name;

    @Schema(example = Example.PHONE_NUMBER, description = Description.SUPPLIER_PHONE_NUMBER)
    private String phoneNumber;

    @Schema(example = Example.PHONE_NUMBER, description = Description.SUPPLIER_CELL_PHONE_NUMBER)
    private String cellPhoneNumber;

    @Schema(example = Example.ADDRESS_POSTAL_CODE, description = Description.ADDRESS_POSTAL_CODE)
    private String postalCode;

    @Schema(example = Example.ADDRESS_STREET, description = Description.ADDRESS_STREET)
    private String street;

    @Schema(example = Example.ADDRESS_STREET_NUMBER, description = Description.ADDRESS_STREET_NUMBER)
    private String streetNumber;

    @Schema(example = Example.ADDRESS_NEIGHBORHOOD, description = Description.ADDRESS_NEIGHBORHOOD)
    private String neighborhood;

    @Schema(example = Example.ADDRESS_COMPLEMENT, description = Description.ADDRESS_COMPLEMENT)
    private String complement;

    private CityStateModel city;

    @Schema(example = Example.EMAIL, description = Description.SUPPLIER_EMAIL)
    private String email;

    @Schema(example = Example.BOOLEAN, description = Description.SUPPLIER_HAS_EVENTS_PLACES)
    private Boolean hasEventsPlaces;

    @Schema(example = Example.BOOLEAN, description = Description.SUPPLIER_ACTIVE)
    private Boolean active;

}
