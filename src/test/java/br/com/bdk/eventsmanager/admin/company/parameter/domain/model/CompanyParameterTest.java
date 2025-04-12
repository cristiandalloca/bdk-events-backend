package br.com.bdk.eventsmanager.admin.company.parameter.domain.model;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.core.ValidatorTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyParameterTest extends ValidatorTest {

    private CompanyParameter companyParameter;

    @BeforeEach
    void setUp() {
        companyParameter = new CompanyParameter();
        companyParameter.setCompany(new Company());
    }

    @Test
    void shouldValidate() {
        var violations = validator.validate(companyParameter);
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldValidateCompanyIsNull() {
        validateFieldIsNull(companyParameter, "company");
    }
}