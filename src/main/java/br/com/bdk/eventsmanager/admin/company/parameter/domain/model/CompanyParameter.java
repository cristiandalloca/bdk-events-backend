package br.com.bdk.eventsmanager.admin.company.parameter.domain.model;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.core.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "company_parameter")
@AllArgsConstructor
@NoArgsConstructor
public class CompanyParameter extends BaseEntity {

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "logo_uri")
    private String logoUri;

    @Column(name = "signature_uri")
    private String signatureUri;

    @Lob
    @Column(columnDefinition = "text", name = "initial_paragraph_contract")
    private String initialParagraphContract;

    @PositiveOrZero
    @Digits(integer = 2, fraction = 2)
    @Column(name = "bill_issuance_fee_value")
    private BigDecimal billIssuanceFeeValue;

    @PositiveOrZero
    @Digits(integer = 2, fraction = 2)
    @Column(name = "credit_card_fee_percentage")
    private BigDecimal creditCardFeePercentage;

    @NotNull
    @PositiveOrZero
    @Digits(integer = 2, fraction = 2)
    @Column(name = "percentage_BDI", nullable = false)
    private BigDecimal percentageBDI = BigDecimal.ZERO;

    @NotNull
    @PositiveOrZero
    @Digits(integer = 2, fraction = 2)
    @Column(name = "max_percentage_seller_commission", nullable = false)
    private BigDecimal maxPercentageSellerCommission = BigDecimal.ZERO;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
