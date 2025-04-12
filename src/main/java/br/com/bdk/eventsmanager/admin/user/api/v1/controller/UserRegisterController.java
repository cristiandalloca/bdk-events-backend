package br.com.bdk.eventsmanager.admin.user.api.v1.controller;

import br.com.bdk.eventsmanager.admin.user.api.v1.assembler.UserModelAssembler;
import br.com.bdk.eventsmanager.admin.user.api.v1.controller.openapi.UserRegisterControllerOpenApi;
import br.com.bdk.eventsmanager.admin.user.api.v1.disassembler.UserInputDisassembler;
import br.com.bdk.eventsmanager.admin.user.api.v1.model.UserModel;
import br.com.bdk.eventsmanager.admin.user.api.v1.model.input.UserChangePasswordInput;
import br.com.bdk.eventsmanager.admin.user.api.v1.model.input.UserInput;
import br.com.bdk.eventsmanager.admin.user.api.v1.model.input.UserWithCompanyInput;
import br.com.bdk.eventsmanager.admin.user.domain.service.UserRegistrationService;
import br.com.bdk.eventsmanager.auth.CheckSecurity;
import br.com.bdk.eventsmanager.core.api.model.PageModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/users")
public class UserRegisterController implements UserRegisterControllerOpenApi {

    private final UserModelAssembler userModelAssembler;
    private final UserInputDisassembler userInputDisassembler;
    private final UserRegistrationService userRegisterService;

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.User.CanCreate
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel create(@RequestBody @Valid UserWithCompanyInput input) {
        return userModelAssembler.toModel(userRegisterService.validateAndSave(userInputDisassembler.toEntity(input)));
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.User.CanRead
    @ResponseStatus(HttpStatus.OK)
    public UserModel findByExternalId(@PathVariable(name = "id") String externalId) {
        return userModelAssembler.toModel(userRegisterService.findByExternalId(externalId));
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.User.CanUpdate
    @ResponseStatus(HttpStatus.OK)
    public UserModel updateByExternalId(@PathVariable(name = "id") String externalId, @RequestBody @Valid UserInput input) {
        var userToUpdate = userRegisterService.findByExternalId(externalId);
        userInputDisassembler.copyToEntity(input, userToUpdate);
        return userModelAssembler.toModel(userRegisterService.validateAndSave(userToUpdate));
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.User.CanRead
    @ResponseStatus(HttpStatus.OK)
    public PageModel<UserModel> findAll(@RequestParam(value = "name", required = false) String name,
                                        @RequestParam(value = "login", required = false) String login, Pageable pageable) {
        return userModelAssembler.toPageModel(userRegisterService.findAll(name, login, pageable));
    }

    @Override
    @DeleteMapping("/{id}")
    @CheckSecurity.User.CanDelete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeByExternalId(@PathVariable(name = "id") String externalId) {
        userRegisterService.removeByExternalId(externalId);
    }

    @Override
    @PutMapping(value = "/{id}/password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.User.CanUpdate
    @ResponseStatus(HttpStatus.OK)
    public void changePasswordByExternalId(@PathVariable(name = "id") String externalId, @RequestBody @Valid UserChangePasswordInput input) {
        userRegisterService.changePasswordByExternalId(externalId, input.getActualPassword(), input.getNewPassword());
    }
}
