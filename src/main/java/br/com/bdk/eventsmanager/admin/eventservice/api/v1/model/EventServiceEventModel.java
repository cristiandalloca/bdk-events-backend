package br.com.bdk.eventsmanager.admin.eventservice.api.v1.model;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventServiceEventModel {

    @Schema(example = SpringDocConstantsUtil.Example.IDENTIFIER, description = SpringDocConstantsUtil.Description.EVENT_IDENTIFIER)
    private String id;

    @Schema(example = SpringDocConstantsUtil.Example.EVENT_NAME, description = SpringDocConstantsUtil.Description.EVENT_NAME)
    private String name;

}
