package br.com.bdk.eventsmanager.admin.event.api.v1.model;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.Collection;

@Builder
@Getter
public class EventModel {

    @Schema(example = Example.IDENTIFIER, description = Description.EVENT_IDENTIFIER)
    private String id;

    @Schema(example = Example.EVENT_NAME, description = Description.EVENT_NAME)
    private String name;

    private Collection<EventTypeModel> types;

    private EventCompanyModel company;

    @Schema(example = Example.BOOLEAN, description = Description.EVENT_ACTIVE)
    private Boolean active;

}
