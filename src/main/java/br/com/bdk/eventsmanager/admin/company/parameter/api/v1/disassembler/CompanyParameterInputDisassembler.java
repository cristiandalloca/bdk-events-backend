package br.com.bdk.eventsmanager.admin.company.parameter.api.v1.disassembler;

import br.com.bdk.eventsmanager.admin.company.parameter.api.v1.model.input.CompanyParameterInput;
import br.com.bdk.eventsmanager.admin.company.parameter.domain.model.CompanyParameter;
import br.com.bdk.eventsmanager.core.api.disassembler.InputDisassembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CompanyParameterInputDisassembler implements InputDisassembler<CompanyParameterInput, CompanyParameter> {

    @Override
    public CompanyParameter toEntity(@NonNull CompanyParameterInput input) {
        CompanyParameter companyParameter = new CompanyParameter();
        this.copyToEntity(input, companyParameter);
        return companyParameter;
    }

    @Override
    public void copyToEntity(@NonNull CompanyParameterInput input, @NonNull CompanyParameter companyParameter) {
        companyParameter.setInitialParagraphContract(input.getInitialParagraphContract());
        companyParameter.setBillIssuanceFeeValue(input.getBillIssuanceFeeValue());
        companyParameter.setCreditCardFeePercentage(input.getCreditCardFeePercentage());
        companyParameter.setPercentageBDI(input.getPercentageBDI());
        companyParameter.setMaxPercentageSellerCommission(input.getMaxPercentageSellerCommission());
    }
}
