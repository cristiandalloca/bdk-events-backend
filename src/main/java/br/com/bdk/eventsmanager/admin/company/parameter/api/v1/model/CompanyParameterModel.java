package br.com.bdk.eventsmanager.admin.company.parameter.api.v1.model;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class CompanyParameterModel {

    @Schema(example = Example.HTML_TEXT, description = Description.COMPANY_SIGNATURE)
    private String initialParagraphContract;

    @Schema(example = Example.DECIMAL, description = Description.COMPANY_BILL_ISSUANCE_FEE_VALUE)
    private BigDecimal billIssuanceFeeValue;

    @Schema(example = Example.PERCENTAGE, description = Description.COMPANY_CREDIT_CARD_FEE_PERCENTAGE)
    private BigDecimal creditCardFeePercentage;

    @Schema(example = Example.PERCENTAGE, description = Description.COMPANY_BDI_PERCENTAGE)
    private BigDecimal percentageBDI;

    @Schema(example = Example.PERCENTAGE, description = Description.COMPANY_MAX_PERCENTAGE_SELLER_COMMISSION)
    private BigDecimal maxPercentageSellerCommission;

}
