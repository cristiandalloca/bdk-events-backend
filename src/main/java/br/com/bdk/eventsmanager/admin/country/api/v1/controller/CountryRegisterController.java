package br.com.bdk.eventsmanager.admin.country.api.v1.controller;

import br.com.bdk.eventsmanager.admin.country.api.v1.assembler.CountryModelAssembler;
import br.com.bdk.eventsmanager.admin.country.api.v1.controller.openapi.CountryRegisterControllerOpenApi;
import br.com.bdk.eventsmanager.admin.country.api.v1.disassembler.CountryInputDisassembler;
import br.com.bdk.eventsmanager.admin.country.api.v1.model.CountryModel;
import br.com.bdk.eventsmanager.admin.country.api.v1.model.input.CountryInput;
import br.com.bdk.eventsmanager.admin.country.domain.service.CountryRegisterService;
import br.com.bdk.eventsmanager.admin.state.api.v1.assembler.StateModelAssembler;
import br.com.bdk.eventsmanager.admin.state.api.v1.model.StateModel;
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
@RequestMapping(path = "/v1/countries")
@RequiredArgsConstructor
public class CountryRegisterController implements CountryRegisterControllerOpenApi {

    private final CountryRegisterService countryRegisterService;
    private final StateRegisterService stateRegisterService;
    private final CountryModelAssembler countryModelAssembler;
    private final CountryInputDisassembler countryInputDisassembler;
    private final StateModelAssembler stateModelAssembler;

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CountryModel findByExternalId(@PathVariable(name = "id") String externalId) {
        return countryModelAssembler.toModel(countryRegisterService.findByExternalId(externalId));
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Country.CanCreate
    @ResponseStatus(HttpStatus.CREATED)
    public CountryModel create(@RequestBody @Valid CountryInput input) {
        var newCountry = countryRegisterService.saveAndValidate(countryInputDisassembler.toEntity(input));
        return countryModelAssembler.toModel(newCountry);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Country.CanUpdate
    @ResponseStatus(HttpStatus.OK)
    public CountryModel updateByExternalId(@PathVariable(name = "id") String externalId, @RequestBody @Valid CountryInput input) {
        var countryToUpdate = countryRegisterService.findByExternalId(externalId);
        countryInputDisassembler.copyToEntity(input, countryToUpdate);
        return countryModelAssembler.toModel(countryRegisterService.saveAndValidate(countryToUpdate));
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PageModel<CountryModel> findAll(@RequestParam(value = "name", required = false) String name, Pageable pageable) {
        return countryModelAssembler.toPageModel(countryRegisterService.findAll(name, pageable));
    }

    @Override
    @GetMapping(value = "/{id}/states", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PageModel<StateModel> findAllStates(@PathVariable(name = "id") String externalCountryId, Pageable pageable) {
        return stateModelAssembler.toPageModel(stateRegisterService.findAll(externalCountryId, null, pageable));
    }

    @Override
    @DeleteMapping("/{id}")
    @CheckSecurity.Country.CanDelete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeByExternalId(@PathVariable(name = "id") String externalId) {
        countryRegisterService.removeByExternalId(externalId);
    }

}
