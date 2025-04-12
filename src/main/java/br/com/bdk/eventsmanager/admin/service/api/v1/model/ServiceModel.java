package br.com.bdk.eventsmanager.admin.service.api.v1.model;

import br.com.bdk.eventsmanager.admin.service.domain.model.ServiceCostType;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ServiceModel {

    @Schema(example = Example.IDENTIFIER, description = Description.SERVICE_IDENTIFIER)
    private String id;

    @Schema(example = Example.SERVICE_NAME, description = Description.SERVICE_NAME)
    private String name;

    @Schema(example = Example.HTML_TEXT)
    private String description;

    private ServiceCompanyModel company;

    @Schema(example = Example.SERVICE_COST_TYPE, description = Description.SERVICE_COST_TYPE)
    private ServiceCostType costType;

    @Schema(example = Example.BOOLEAN, description = Description.SERVICE_APPLY_BDI)
    private Boolean applyBDI;

    @Schema(example = Example.BOOLEAN, description = Description.SERVICE_APPLY_SELLER_COMMISSION)
    private Boolean applySellerCommission;

    @Schema(example = Example.BOOLEAN, description = Description.SERVICE_ACTIVE)
    private Boolean active;

    @Builder
    @Getter
    public static class ServiceCompanyModel {

        @Schema(example = Example.IDENTIFIER, description = Description.COMPANY_IDENTIFIER)
        private String id;

        @Schema(example = Example.COMPANY_NAME, description = Description.COMPANY_NAME)
        private String name;

        @Schema(example = Example.COMPANY_BUSINESS_NAME, description = Description.COMPANY_BUSINESS_NAME)
        private String businessName;

    }

}
