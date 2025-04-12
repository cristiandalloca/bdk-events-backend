package br.com.bdk.eventsmanager.admin.country.api.v1.model.input;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import br.com.bdk.eventsmanager.core.validator.OnlyLetters;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CountryInput {

    @NotBlank
    @Size(max = 255)
    @OnlyLetters
    @Schema(example = Example.COUNTRY_NAME, description = Description.COUNTRY_NAME)
    private String name;

    @NotBlank
    @Size(min = 2, max = 3)
    @OnlyLetters
    @Schema(example = Example.COUNTRY_ACRONYM, description = Description.COUNTRY_ACRONYM)
    private String acronym;
}
