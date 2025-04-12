package br.com.bdk.eventsmanager.admin.city.api.v1.model.input;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import br.com.bdk.eventsmanager.core.validator.OnlyLetters;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CityInput {

    @NotBlank
    @Size(max = 255)
    @OnlyLetters
    @Schema(example = Example.CITY_NAME, description = Description.CITY_NAME)
    private String name;

    @NotBlank
    @Size(max = 36) // TODO: Criar validação para ids UUID
    @Schema(example = Example.IDENTIFIER, description = Description.STATE_IDENTIFIER)
    private String stateId;
}
