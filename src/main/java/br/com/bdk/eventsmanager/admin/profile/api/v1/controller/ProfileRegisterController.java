package br.com.bdk.eventsmanager.admin.profile.api.v1.controller;

import br.com.bdk.eventsmanager.admin.company.domain.validator.CompanyAccessService;
import br.com.bdk.eventsmanager.admin.profile.api.v1.assembler.ProfileCompleteModelAssembler;
import br.com.bdk.eventsmanager.admin.profile.api.v1.assembler.ProfileModelAssembler;
import br.com.bdk.eventsmanager.admin.profile.api.v1.controller.openapi.ProfileRegisterControllerOpenApi;
import br.com.bdk.eventsmanager.admin.profile.api.v1.disassembler.ProfileInputDisassembler;
import br.com.bdk.eventsmanager.admin.profile.api.v1.model.ProfileCompleteModel;
import br.com.bdk.eventsmanager.admin.profile.api.v1.model.ProfileModel;
import br.com.bdk.eventsmanager.admin.profile.api.v1.model.input.ProfileInput;
import br.com.bdk.eventsmanager.admin.profile.domain.service.ProfileRegistrationService;
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
@RequestMapping(path = "/v1/profiles")
public class ProfileRegisterController implements ProfileRegisterControllerOpenApi {

    private final ProfileCompleteModelAssembler profileCompleteModelAssembler;
    private final ProfileModelAssembler profileModelAssembler;
    private final ProfileInputDisassembler profileInputDisassembler;
    private final ProfileRegistrationService profileRegisterService;
    private final CompanyAccessService companyAccessService;

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Profile.CanCreate
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileCompleteModel create(@RequestBody @Valid ProfileInput input) {
        var newProfile = profileRegisterService.validateAndSave(profileInputDisassembler.toEntity(input));
        return profileCompleteModelAssembler.toModel(profileRegisterService.validateAndSave(newProfile));
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Profile.CanRead
    @ResponseStatus(HttpStatus.OK)
    public ProfileCompleteModel findByExternalId(@PathVariable(name = "id") String externalId) {
        return profileCompleteModelAssembler.toModel(profileRegisterService.findByExternalId(externalId));
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Profile.CanUpdate
    @ResponseStatus(HttpStatus.OK)
    public ProfileCompleteModel updateByExternalId(@PathVariable(name = "id") String externalId, @RequestBody @Valid ProfileInput input) {
        var profileToUpdate = profileRegisterService.findByExternalId(externalId);
        profileInputDisassembler.copyToEntity(input, profileToUpdate);
        return profileCompleteModelAssembler.toModel(profileRegisterService.validateAndSave(profileToUpdate));
    }

    @Override
    @DeleteMapping("/{id}")
    @CheckSecurity.Profile.CanDelete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeByExternalId(@PathVariable(name = "id") String externalId) {
        profileRegisterService.removeByExternalId(externalId);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Profile.CanRead
    @ResponseStatus(HttpStatus.OK)
    public PageModel<ProfileModel> findAll(@RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "companyId", required = false) String companyExternalId,
                                           Pageable pageable) {
        companyAccessService.validate(companyExternalId);
        return profileModelAssembler.toPageModel(profileRegisterService.findAll(name, companyExternalId, pageable));
    }
}
