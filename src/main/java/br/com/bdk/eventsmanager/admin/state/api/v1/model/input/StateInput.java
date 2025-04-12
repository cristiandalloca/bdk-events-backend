package br.com.bdk.eventsmanager.admin.state.api.v1.model.input;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import br.com.bdk.eventsmanager.core.validator.OnlyLetters;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class StateInput {

    @NotBlank
    @Size(max = 255)
    @OnlyLetters
    @Schema(example = Example.STATE_NAME, description = Description.STATE_NAME)
    private String name;

    @NotBlank
    @Size(min = 2, max = 3)
    @OnlyLetters
    @Schema(example = Example.STATE_ACRONYM, description = Description.STATE_ACRONYM)
    private String acronym;

    @NotBlank
    @Size(max = 36)
    @Schema(example = Example.IDENTIFIER, description = Description.COUNTRY_IDENTIFIER)
    private String countryId;
}
