package br.com.bdk.eventsmanager.admin.service.api.v1.model.input;

import br.com.bdk.eventsmanager.admin.service.domain.model.ServiceCostType;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ServiceInput {

    @NotBlank
    @Size(max = 255)
    @Schema(example = Example.SERVICE_NAME, description = Description.SERVICE_NAME)
    private String name;

    @Schema(example = Example.HTML_TEXT, description = Description.SERVICE_DESCRIPTION)
    private String description;

    @NotBlank
    @Size(max = 36)
    @Schema(example = Example.IDENTIFIER, description = Description.COMPANY_IDENTIFIER)
    private String companyId;

    @NotNull
    @Schema(example = Example.SERVICE_COST_TYPE, description = Description.SERVICE_COST_TYPE)
    private ServiceCostType costType;

    @NotNull
    @Schema(example = Example.BOOLEAN, description = Description.SERVICE_APPLY_BDI)
    private Boolean applyBDI;

    @NotNull
    @Schema(example = Example.BOOLEAN, description = Description.SERVICE_APPLY_SELLER_COMMISSION)
    private Boolean applySellerCommission;

    @NotNull
    @Schema(example = Example.BOOLEAN, description = Description.SERVICE_ACTIVE)
    private Boolean active;

}
