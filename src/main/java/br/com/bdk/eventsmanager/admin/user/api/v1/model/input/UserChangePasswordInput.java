package br.com.bdk.eventsmanager.admin.user.api.v1.model.input;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import br.com.bdk.eventsmanager.core.validator.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserChangePasswordInput {

    @NotBlank
    @Schema(example = Example.PASSWORD, description = Description.USER_ACTUAL_PASSWORD)
    private String actualPassword;

    @Password
    @Schema(example = Example.PASSWORD, description = Description.USER_NEW_PASSWORD)
    private String newPassword;

}
