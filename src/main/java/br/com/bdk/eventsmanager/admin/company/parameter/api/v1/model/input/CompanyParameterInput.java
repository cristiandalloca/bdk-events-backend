package br.com.bdk.eventsmanager.admin.company.parameter.api.v1.model.input;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CompanyParameterInput {

    @Schema(example = Example.HTML_TEXT, description = SpringDocConstantsUtil.Description.COMPANY_INITIAL_PARAGRAPH_CONTRACT)
    private String initialParagraphContract;

    @PositiveOrZero
    @Digits(integer = 2, fraction = 2)
    @Schema(example = Example.DECIMAL, description = SpringDocConstantsUtil.Description.COMPANY_BILL_ISSUANCE_FEE_VALUE)
    private BigDecimal billIssuanceFeeValue;

    @PositiveOrZero
    @Digits(integer = 2, fraction = 2)
    @Schema(example = Example.PERCENTAGE, description = SpringDocConstantsUtil.Description.COMPANY_CREDIT_CARD_FEE_PERCENTAGE)
    private BigDecimal creditCardFeePercentage;

    @NotNull
    @PositiveOrZero
    @Digits(integer = 2, fraction = 2)
    @Schema(example = Example.PERCENTAGE, description = SpringDocConstantsUtil.Description.COMPANY_BDI_PERCENTAGE)
    private BigDecimal percentageBDI;

    @NotNull
    @PositiveOrZero
    @Digits(integer = 2, fraction = 2)
    @Schema(example = Example.PERCENTAGE, description = SpringDocConstantsUtil.Description.COMPANY_MAX_PERCENTAGE_SELLER_COMMISSION)
    private BigDecimal maxPercentageSellerCommission;
}
