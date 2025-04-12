package br.com.bdk.eventsmanager.admin.event.api.v1.model;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventTypeModel {

    @Schema(example = Example.EVENT_TYPE, description = Description.EVENT_TYPE)
    private String id;

    @Schema(example = Example.EVENT_TYPE_DESCRIPTION, description = Description.EVENT_TYPE_DESCRIPTION)
    private String description;

}
