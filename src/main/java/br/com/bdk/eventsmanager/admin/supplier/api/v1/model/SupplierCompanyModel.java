package br.com.bdk.eventsmanager.admin.supplier.api.v1.model;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SupplierCompanyModel {

    @Schema(example = Example.IDENTIFIER, description = Description.COMPANY_IDENTIFIER)
    private String id;

    @Schema(example = Example.COMPANY_NAME, description = Description.COMPANY_NAME)
    private String name;

    @Schema(example = Example.COMPANY_BUSINESS_NAME, description = Description.COMPANY_BUSINESS_NAME)
    private String businessName;
}
