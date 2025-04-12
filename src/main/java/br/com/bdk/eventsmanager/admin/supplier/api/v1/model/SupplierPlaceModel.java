package br.com.bdk.eventsmanager.admin.supplier.api.v1.model;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SupplierPlaceModel {

    @Schema(example = SpringDocConstantsUtil.Example.IDENTIFIER, description = SpringDocConstantsUtil.Description.SUPPLIER_PLACE_IDENTIFIER)
    private String id;

    @Schema(example = SpringDocConstantsUtil.Example.SUPPLIER_PLACE_NAME, description = SpringDocConstantsUtil.Description.SUPPLIER_PLACE_NAME)
    private String name;

    @Schema(example = SpringDocConstantsUtil.Example.HTML_TEXT, description = SpringDocConstantsUtil.Description.SUPPLIER_PLACE_DESCRIPTION)
    private String description;

    @Schema(example = SpringDocConstantsUtil.Example.IDENTIFIER, description = SpringDocConstantsUtil.Description.SUPPLIER_PLACE_CAPACITY)
    private Integer maximumCapacityPeople;

    @Schema(example = SpringDocConstantsUtil.Example.BOOLEAN, description = SpringDocConstantsUtil.Description.SUPPLIER_PLACE_ACTIVE)
    private Boolean active;

}
