package br.com.bdk.eventsmanager.admin.user.api.v1.model.input;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import br.com.bdk.eventsmanager.core.util.RegexConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserInput {

    @NotBlank
    @Size(max = 255)
    @Schema(example = Example.USER_NAME, description = Description.USER_NAME)
    private String name;

    @NotBlank
    @Email(regexp = RegexConstants.EMAIL_REGEX)
    @Size(max = 124)
    @Schema(example = Example.EMAIL, description = Description.USER_EMAIL)
    private String email;

    @NotBlank
    @Size(max = 50)
    @Schema(example = Example.USER_LOGIN, description = Description.USER_LOGIN)
    private String login;

    @NotNull
    @Schema(example = Example.BOOLEAN, description = Description.USER_ACTIVE)
    private boolean active;

}
