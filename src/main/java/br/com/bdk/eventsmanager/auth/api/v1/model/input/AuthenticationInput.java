package br.com.bdk.eventsmanager.auth.api.v1.model.input;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthenticationInput {

    @NotBlank
    @Schema(example = Example.USER_LOGIN, description = Description.USERNAME)
    private String username;

    @NotBlank
    @Schema(example = Example.PASSWORD, description = Description.PASSWORD)
    private String password;

}
