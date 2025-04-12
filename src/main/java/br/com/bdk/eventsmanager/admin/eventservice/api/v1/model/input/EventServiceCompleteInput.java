package br.com.bdk.eventsmanager.admin.eventservice.api.v1.model.input;

import br.com.bdk.eventsmanager.admin.eventservice.domain.model.EventServiceMeasurementType;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class EventServiceCompleteInput {

    @NotBlank
    @Size(max = 36)
    @Schema(example = Example.IDENTIFIER, description = Description.EVENT_IDENTIFIER)
    private String eventId;

    @NotBlank
    @Size(max = 36)
    @Schema(example = Example.IDENTIFIER, description = Description.SERVICE_IDENTIFIER)
    private String serviceId;

    @PositiveOrZero
    @Digits(integer = 6, fraction = 2)
    @Schema(example = Example.DECIMAL, description = Description.EVENT_SERVICE_PRICE)
    private BigDecimal price;

    @PositiveOrZero
    @Digits(integer = 6, fraction = 2)
    @Schema(example = Example.DECIMAL, description = Description.EVENT_SERVICE_MINIMUM_PRICE)
    private BigDecimal minimumPrice;

    @PositiveOrZero
    @Schema(example = Example.INTEGER, description = Description.EVENT_SERVICE_QUANTITY)
    private Integer quantity;

    @NotNull
    @Schema(description = Description.EVENT_SERVICE_DISPLAY_IN_PROPOSAL, example = Example.BOOLEAN)
    private Boolean displayInProposal;

    @NotNull
    @Schema(description = Description.EVENT_SERVICE_DISPLAY_PRICE_IN_PROPOSAL, example = Example.BOOLEAN)
    private Boolean displayPriceInProposal;

    @NotNull
    @Schema(description = Description.EVENT_SERVICE_ALLOW_CHANGE_QUANTITY, example = Example.BOOLEAN)
    private Boolean allowChangeQuantity;

    @NotNull
    @Schema(description = Description.EVENT_SERVICE_ALLOW_CHANGE_PRICE, example = Example.BOOLEAN)
    private Boolean allowChangePrice;

    @NotNull
    @Schema(description = Description.EVENT_SERVICE_MEASUREMENT_TYPE_IDENTIFIER, example = Example.EVENT_SERVICE_MEASUREMENT_TYPE_IDENTIFIER)
    private EventServiceMeasurementType measurementType;

    @Schema(description = Description.COMPANY_INVITATION_TYPE_IDENTIFIER, example = Example.IDENTIFIER)
    private String companyInvitationTypeId;

}
