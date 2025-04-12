package br.com.bdk.eventsmanager.admin.eventservice.api.v1.model;

import br.com.bdk.eventsmanager.admin.service.domain.model.ServiceCostType;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventServiceServiceModel {

    @Schema(example = SpringDocConstantsUtil.Example.IDENTIFIER, description = SpringDocConstantsUtil.Description.SERVICE_IDENTIFIER)
    private String id;

    @Schema(example = SpringDocConstantsUtil.Example.SERVICE_NAME, description = SpringDocConstantsUtil.Description.SERVICE_NAME)
    private String name;

    @Schema(example = SpringDocConstantsUtil.Example.SERVICE_COST_TYPE, description = SpringDocConstantsUtil.Description.SERVICE_COST_TYPE)
    private ServiceCostType costType;

    @Schema(example = SpringDocConstantsUtil.Example.BOOLEAN, description = SpringDocConstantsUtil.Description.SERVICE_APPLY_BDI)
    private Boolean applyBDI;

    @Schema(example = SpringDocConstantsUtil.Example.BOOLEAN, description = SpringDocConstantsUtil.Description.SERVICE_APPLY_SELLER_COMMISSION)
    private Boolean applySellerCommission;

    @Schema(example = SpringDocConstantsUtil.Example.BOOLEAN, description = SpringDocConstantsUtil.Description.SERVICE_ACTIVE)
    private Boolean active;

}
