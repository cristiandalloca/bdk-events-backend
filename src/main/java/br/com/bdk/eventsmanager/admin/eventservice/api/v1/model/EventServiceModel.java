package br.com.bdk.eventsmanager.admin.eventservice.api.v1.model;

import br.com.bdk.eventsmanager.admin.company.invitationtype.api.v1.model.CompanyInvitationTypeModel;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class EventServiceModel {

    @Schema(description = Description.EVENT_SERVICE_IDENTIFIER, example = Example.IDENTIFIER)
    private String id;

    private EventServiceServiceModel service;

    @Schema(description = Description.EVENT_SERVICE_PRICE, example = Example.DECIMAL)
    private BigDecimal price;

    @Schema(description = Description.EVENT_SERVICE_MINIMUM_PRICE, example = Example.DECIMAL)
    private BigDecimal minimumPrice;

    @Schema(description = Description.EVENT_SERVICE_QUANTITY, example = Example.INTEGER)
    private Integer quantity;

    @Schema(description = Description.EVENT_SERVICE_DISPLAY_IN_PROPOSAL, example = Example.BOOLEAN)
    private boolean displayInProposal;

    @Schema(description = Description.EVENT_SERVICE_DISPLAY_PRICE_IN_PROPOSAL, example = Example.BOOLEAN)
    private boolean displayPriceInProposal;

    @Schema(description = Description.EVENT_SERVICE_ALLOW_CHANGE_QUANTITY, example = Example.BOOLEAN)
    private boolean allowChangeQuantity;

    @Schema(description = Description.EVENT_SERVICE_ALLOW_CHANGE_PRICE, example = Example.BOOLEAN)
    private boolean allowChangePrice;

    private EventServiceMeasurementTypeModel measurementType;

    private CompanyInvitationTypeModel companyInvitationType;

}
