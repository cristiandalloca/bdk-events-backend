package br.com.bdk.eventsmanager.admin.eventservice.api.v1.model;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventServiceMeasurementTypeModel {

    @Schema(description = Description.EVENT_SERVICE_MEASUREMENT_TYPE_IDENTIFIER, example = Example.EVENT_SERVICE_MEASUREMENT_TYPE_IDENTIFIER)
    private String id;

    @Schema(description = Description.EVENT_SERVICE_MEASUREMENT_TYPE_DESCRIPTION, example = Example.EVENT_SERVICE_MEASUREMENT_TYPE_DESCRIPTION)
    private String description;

}
