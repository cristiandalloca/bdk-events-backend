package br.com.bdk.eventsmanager.admin.user.api.v1.controller;

import br.com.bdk.eventsmanager.admin.user.api.v1.assembler.UserProfileModelAssembler;
import br.com.bdk.eventsmanager.admin.user.api.v1.controller.openapi.UserProfileRegisterControllerOpenApi;
import br.com.bdk.eventsmanager.admin.user.api.v1.model.UserProfileModel;
import br.com.bdk.eventsmanager.admin.user.domain.service.UserProfileRegisterService;
import br.com.bdk.eventsmanager.admin.user.domain.service.UserRegistrationService;
import br.com.bdk.eventsmanager.auth.CheckSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/users/{id}/profiles")
public class UserProfileRegisterController implements UserProfileRegisterControllerOpenApi {

    private final UserRegistrationService userRegisterService;
    private final UserProfileRegisterService userProfileRegisterService;
    private final UserProfileModelAssembler userProfileModelAssembler;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.User.CanRead
    @ResponseStatus(HttpStatus.OK)
    public Collection<UserProfileModel> findProfiles(@PathVariable(name = "id") String userExternalId) {
        return userProfileModelAssembler.toCollectionModel(userRegisterService.findByExternalIdWithProfiles(userExternalId).getProfiles());
    }

    @Override
    @PutMapping("/{profileId}")
    @CheckSecurity.User.CanUpdate
    @ResponseStatus(HttpStatus.OK)
    public void addProfile(@PathVariable(name = "id") String userExternalId, @PathVariable(name = "profileId") String profileId) {
        userProfileRegisterService.addProfile(userExternalId, profileId);
    }

    @Override
    @DeleteMapping("/{profileId}")
    @CheckSecurity.User.CanUpdate
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProfile(@PathVariable(name = "id") String userExternalId, @PathVariable(name = "profileId") String profileId) {
        userProfileRegisterService.removeProfile(userExternalId, profileId);
    }
}
