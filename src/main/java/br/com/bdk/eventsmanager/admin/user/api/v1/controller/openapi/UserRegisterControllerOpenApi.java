package br.com.bdk.eventsmanager.admin.user.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.admin.user.api.v1.model.UserModel;
import br.com.bdk.eventsmanager.admin.user.api.v1.model.input.UserChangePasswordInput;
import br.com.bdk.eventsmanager.admin.user.api.v1.model.input.UserInput;
import br.com.bdk.eventsmanager.admin.user.api.v1.model.input.UserWithCompanyInput;
import br.com.bdk.eventsmanager.core.api.model.PageModel;
import br.com.bdk.eventsmanager.core.api.openapi.ParameterQueryPageable;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;

@Tag(name = "Users")
@SecurityRequirement(name = "bearerAuth")
public interface UserRegisterControllerOpenApi {

    @Operation(summary = "Create a user")
    UserModel create(UserWithCompanyInput input);

    @Operation(summary = "Find a user by id")
    UserModel findByExternalId(@Parameter(description = Description.USER_IDENTIFIER, example = Example.IDENTIFIER) String externalId);

    @Operation(summary = "Update a user by id")
    UserModel updateByExternalId(@Parameter(description = Description.USER_IDENTIFIER, example = Example.IDENTIFIER) String externalId,
                                 UserInput input);

    @Operation(summary = "Search users")
    @ParameterQueryPageable
    PageModel<UserModel> findAll(@Parameter(description = Description.USER_NAME) String name,
                                 @Parameter(description = Description.USER_LOGIN) String login,
                                 @Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Remove a user by id")
    void removeByExternalId(@Parameter(description = Description.USER_IDENTIFIER, example = Example.IDENTIFIER) String externalId);

    @Operation(summary = "Change password")
    void changePasswordByExternalId(@Parameter(description = Description.USER_IDENTIFIER, example = Example.IDENTIFIER) String externalId,
                                    UserChangePasswordInput input);
}
