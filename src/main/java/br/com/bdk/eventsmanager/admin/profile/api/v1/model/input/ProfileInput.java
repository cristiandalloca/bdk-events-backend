package br.com.bdk.eventsmanager.admin.profile.api.v1.model.input;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProfileInput {

    @NotBlank
    @Size(max = 255)
    @Schema(example = Example.PROFILE_NAME, description = Description.PROFILE_NAME)
    private String name;

    @NotBlank
    @Size(max = 36)
    @Schema(example = Example.IDENTIFIER, description = Description.COMPANY_IDENTIFIER)
    @JsonProperty("companyId")
    private String companyExternalId;

    @Schema(example = Example.IDENTIFIER_ARRAY, description = Description.PROFILE_PRIVILEGES_IDENTIFIER)
    @JsonProperty("privilegesIds")
    private List<String> privilegesExternalIds = new ArrayList<>();

}
