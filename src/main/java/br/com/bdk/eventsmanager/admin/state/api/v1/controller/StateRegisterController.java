package br.com.bdk.eventsmanager.admin.state.api.v1.controller;

import br.com.bdk.eventsmanager.admin.state.api.v1.assembler.StateCountryModelAssembler;
import br.com.bdk.eventsmanager.admin.state.api.v1.controller.openapi.StateRegisterControllerOpenApi;
import br.com.bdk.eventsmanager.admin.state.api.v1.disassembler.StateInputDisassembler;
import br.com.bdk.eventsmanager.admin.state.api.v1.model.StateCountryModel;
import br.com.bdk.eventsmanager.admin.state.api.v1.model.input.StateInput;
import br.com.bdk.eventsmanager.admin.state.domain.service.StateRegisterService;
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
@RequestMapping("/v1/states")
@RequiredArgsConstructor
public class StateRegisterController implements StateRegisterControllerOpenApi {

    private final StateRegisterService stateRegisterService;
    private final StateCountryModelAssembler stateCountryModelAssembler;
    private final StateInputDisassembler stateInputDisassembler;

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public StateCountryModel findByExternalId(@PathVariable(name = "id") String externalId) {
        return stateCountryModelAssembler.toModel(stateRegisterService.findByExternalId(externalId));
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.State.CanCreate
    @ResponseStatus(HttpStatus.CREATED)
    public StateCountryModel create(@RequestBody @Valid StateInput input) {
        var newState = stateRegisterService.saveAndValidate(stateInputDisassembler.toEntity(input));
        return stateCountryModelAssembler.toModel(newState);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.State.CanUpdate
    @ResponseStatus(HttpStatus.OK)
    public StateCountryModel updateByExternalId(@PathVariable(name = "id") String externalId, @RequestBody @Valid StateInput input) {
        var stateToUpdate = stateRegisterService.findByExternalId(externalId);
        stateInputDisassembler.copyToEntity(input, stateToUpdate);
        return stateCountryModelAssembler.toModel(stateRegisterService.saveAndValidate(stateToUpdate));
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PageModel<StateCountryModel> findAll(@RequestParam(value = "countryId", required = false) String externalCountryId,
                                                @RequestParam(value = "name", required = false) String name,
                                                Pageable pageable) {
        return stateCountryModelAssembler.toPageModel(stateRegisterService.findAll(externalCountryId, name, pageable));
    }

    @Override
    @DeleteMapping("/{id}")
    @CheckSecurity.State.CanDelete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeByExternalId(@PathVariable(name = "id") String externalId) {
        stateRegisterService.removeByExternalId(externalId);
    }
}
