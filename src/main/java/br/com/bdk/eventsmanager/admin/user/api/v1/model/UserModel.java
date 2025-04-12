package br.com.bdk.eventsmanager.admin.user.api.v1.model;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserModel {

    @Schema(example = Example.IDENTIFIER, description = Description.USER_IDENTIFIER)
    private String id;

    @Schema(example = Example.USER_NAME, description = Description.USER_NAME)
    private String name;

    @Schema(example = Example.EMAIL, description = Description.USER_EMAIL)
    private String email;

    @Schema(example = Example.USER_LOGIN, description = Description.USER_LOGIN)
    private String login;

    @Schema(example = Example.BOOLEAN, description =  Description.USER_ACTIVE)
    private boolean active;

    private UserCompanyModel company;

}
