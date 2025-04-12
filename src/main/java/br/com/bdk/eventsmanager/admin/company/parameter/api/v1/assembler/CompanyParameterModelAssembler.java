package br.com.bdk.eventsmanager.admin.company.parameter.api.v1.assembler;

import br.com.bdk.eventsmanager.admin.company.parameter.api.v1.model.CompanyParameterModel;
import br.com.bdk.eventsmanager.admin.company.parameter.domain.model.CompanyParameter;
import br.com.bdk.eventsmanager.core.api.assembler.ModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CompanyParameterModelAssembler implements ModelAssembler<CompanyParameter, CompanyParameterModel> {

    @NonNull
    @Override
    public CompanyParameterModel toModel(@NonNull CompanyParameter companyParameter) {
        return CompanyParameterModel.builder()
                .initialParagraphContract(companyParameter.getInitialParagraphContract())
                .billIssuanceFeeValue(companyParameter.getBillIssuanceFeeValue())
                .creditCardFeePercentage(companyParameter.getCreditCardFeePercentage())
                .percentageBDI(companyParameter.getPercentageBDI())
                .maxPercentageSellerCommission(companyParameter.getMaxPercentageSellerCommission())
                .build();
    }
}
