package br.com.bdk.eventsmanager.admin.event.api.v1.model.input;

import br.com.bdk.eventsmanager.admin.event.domain.model.EventType;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class EventInput {

    @NotBlank
    @Size(max = 255)
    @Schema(example = Example.EVENT_NAME, description = Description.EVENT_NAME)
    private String name;

    @NotBlank
    @Size(max = 36)
    @Schema(example = Example.IDENTIFIER, description = Description.COMPANY_IDENTIFIER)
    private String companyId;

    @Schema(example = Example.EVENT_TYPES, description = Description.EVENT_TYPE)
    private EventType[] types;

    @NotNull
    @Schema(example = Example.BOOLEAN, description = Description.EVENT_ACTIVE)
    private Boolean active;
}
