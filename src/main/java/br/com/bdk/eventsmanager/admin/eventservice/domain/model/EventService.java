package br.com.bdk.eventsmanager.admin.eventservice.domain.model;

import br.com.bdk.eventsmanager.admin.company.invitationtype.domain.model.CompanyInvitationType;
import br.com.bdk.eventsmanager.admin.event.domain.model.Event;
import br.com.bdk.eventsmanager.admin.service.domain.model.Service;
import br.com.bdk.eventsmanager.core.entity.ExposedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "event_service")
@NoArgsConstructor
public class EventService extends ExposedEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @PositiveOrZero
    @Digits(integer = 7, fraction = 2)
    @Column(name = "price", scale = 9, precision = 2)
    private BigDecimal price = BigDecimal.ZERO;

    @PositiveOrZero
    @Digits(integer = 7, fraction = 2)
    @Column(name = "minimum_price", scale = 9, precision = 2)
    private BigDecimal minimumPrice = BigDecimal.ZERO;

    @PositiveOrZero
    @Column(name = "quantity")
    private Integer quantity = NumberUtils.INTEGER_ONE;

    @Column(name = "display_in_proposal", nullable = false)
    private boolean displayInProposal = true;

    @Column(name = "display_price_in_proposal", nullable = false)
    private boolean displayPriceInProposal = true;

    @Column(name = "allow_change_quantity", nullable = false)
    private boolean allowChangeQuantity = true;

    @Column(name = "allow_change_price", nullable = false)
    private boolean allowChangePrice = true;

    @NotNull
    @Column(name = "measurement_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventServiceMeasurementType measurementType = EventServiceMeasurementType.UNIQUE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_invitation_type_id")
    private CompanyInvitationType companyInvitationType;

    public EventService(Event event, Service service) {
        this();
        this.event = event;
        this.service = service;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
