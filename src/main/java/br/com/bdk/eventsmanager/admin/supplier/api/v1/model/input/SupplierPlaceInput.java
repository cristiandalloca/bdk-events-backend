package br.com.bdk.eventsmanager.admin.supplier.api.v1.model.input;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SupplierPlaceInput {

    @NotBlank
    @Size(max = 255)
    @Schema(example = Example.SUPPLIER_PLACE_NAME, description = Description.SUPPLIER_PLACE_NAME)
    private String name;

    @Schema(example = Example.HTML_TEXT, description = Description.SUPPLIER_PLACE_DESCRIPTION)
    private String description;

    @NotNull
    @Positive
    @Schema(example = Example.IDENTIFIER, description = Description.SUPPLIER_PLACE_CAPACITY)
    private Integer maximumCapacityPeople;

    @NotNull
    @Schema(example = Example.BOOLEAN, description = Description.SUPPLIER_PLACE_ACTIVE)
    private Boolean active;

}
