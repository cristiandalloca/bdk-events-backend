package br.com.bdk.eventsmanager.admin.educationalinstitution.api.v1.controller;

import br.com.bdk.eventsmanager.admin.educationalinstitution.api.v1.assembler.EducationalInstitutionModelAssembler;
import br.com.bdk.eventsmanager.admin.educationalinstitution.api.v1.assembler.EducationalInstitutionTypeModelAssembler;
import br.com.bdk.eventsmanager.admin.educationalinstitution.api.v1.controller.openapi.EducationalInstitutionRegisterControllerOpenApi;
import br.com.bdk.eventsmanager.admin.educationalinstitution.api.v1.disassembler.EducationalInstitutionInputDisassembler;
import br.com.bdk.eventsmanager.admin.educationalinstitution.api.v1.model.EducationalInstitutionModel;
import br.com.bdk.eventsmanager.admin.educationalinstitution.api.v1.model.EducationalInstitutionTypeModel;
import br.com.bdk.eventsmanager.admin.educationalinstitution.api.v1.model.input.EducationalInstitutionInput;
import br.com.bdk.eventsmanager.admin.educationalinstitution.domain.model.EducationalInstitutionType;
import br.com.bdk.eventsmanager.admin.educationalinstitution.domain.service.EducationalInstitutionRegistrationService;
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

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/educational-institutions")
public class EducationalInstitutionRegisterController implements EducationalInstitutionRegisterControllerOpenApi {

    private final EducationalInstitutionInputDisassembler disassembler;
    private final EducationalInstitutionRegistrationService service;
    private final EducationalInstitutionModelAssembler assembler;
    private final EducationalInstitutionTypeModelAssembler typeModelAssembler;

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.EducationalInstitution.CanCreate
    @ResponseStatus(HttpStatus.CREATED)
    public EducationalInstitutionModel create(@RequestBody @Valid EducationalInstitutionInput input) {
        return assembler.toModel(service.validateAndSave(disassembler.toEntity(input)));
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.EducationalInstitution.CanRead
    @ResponseStatus(HttpStatus.OK)
    public EducationalInstitutionModel findByExternalId(@PathVariable("id") String externalId) {
        return assembler.toModel(service.findByExternalId(externalId));
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.EducationalInstitution.CanUpdate
    @ResponseStatus(HttpStatus.OK)
    public EducationalInstitutionModel updateByExternalId(@PathVariable("id") String externalId, @RequestBody @Valid EducationalInstitutionInput input) {
        var educationalInstitutionToUpdate = service.findByExternalId(externalId);
        disassembler.copyToEntity(input, educationalInstitutionToUpdate);
        return assembler.toModel(service.validateAndSave(educationalInstitutionToUpdate));
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckSecurity.EducationalInstitution.CanDelete
    public void removeByExternalId(@PathVariable("id") String externalId) {
        service.removeByExternalId(externalId);
    }

    @Override
    @GetMapping(value = "/types", produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.EducationalInstitution.CanRead
    @ResponseStatus(HttpStatus.OK)
    public Collection<EducationalInstitutionTypeModel> findTypes() {
        return typeModelAssembler.toCollectionModel(List.of(EducationalInstitutionType.values()));
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.EducationalInstitution.CanRead
    @ResponseStatus(HttpStatus.OK)
    public PageModel<EducationalInstitutionModel> findAll(@RequestParam(value = "name", required = false) String name,
                                                          @RequestParam(value = "cityId", required = false) String cityExternalId,
                                                          @RequestParam(value = "type", required = false) EducationalInstitutionType type,
                                                          @RequestParam(value = "active", required = false) Boolean active, Pageable pageable) {
        return assembler.toPageModel(service.findAll(name, cityExternalId, type, active, pageable));
    }


}
