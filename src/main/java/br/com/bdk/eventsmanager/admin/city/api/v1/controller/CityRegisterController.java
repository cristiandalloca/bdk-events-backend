package br.com.bdk.eventsmanager.admin.city.api.v1.controller;

import br.com.bdk.eventsmanager.admin.city.api.v1.assembler.CityStateCountryAssembler;
import br.com.bdk.eventsmanager.admin.city.api.v1.controller.openapi.CityRegisterControllerOpenApi;
import br.com.bdk.eventsmanager.admin.city.api.v1.disassembler.CityInputDisassembler;
import br.com.bdk.eventsmanager.admin.city.api.v1.model.CityStateCountryModel;
import br.com.bdk.eventsmanager.admin.city.api.v1.model.input.CityInput;
import br.com.bdk.eventsmanager.admin.city.domain.service.CityRegistrationService;
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
@RequestMapping("/v1/cities")
@RequiredArgsConstructor
public class CityRegisterController implements CityRegisterControllerOpenApi {

    private final CityStateCountryAssembler cityStateCountryAssembler;
    private final CityRegistrationService cityRegisterService;
    private final CityInputDisassembler cityInputDisassembler;

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CityStateCountryModel findByExternalId(@PathVariable(name = "id") String externalId) {// TODO: Criar validação para ids UUID
        return cityStateCountryAssembler.toModel(cityRegisterService.findByExternalId(externalId));
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.City.CanCreate
    @ResponseStatus(HttpStatus.CREATED)
    public CityStateCountryModel create(@RequestBody @Valid CityInput input) {
        var newCity = cityRegisterService.validateAndSave(cityInputDisassembler.toEntity(input));
        return cityStateCountryAssembler.toModel(newCity);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.City.CanUpdate
    @ResponseStatus(HttpStatus.OK)
    public CityStateCountryModel updateByExternalId(@PathVariable(name = "id") String externalId, @RequestBody @Valid CityInput input) {// TODO: Criar validação para ids UUID
        var cityToUpdate = cityRegisterService.findByExternalId(externalId);
        cityInputDisassembler.copyToEntity(input, cityToUpdate);
        return cityStateCountryAssembler.toModel(cityRegisterService.validateAndSave(cityToUpdate));
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PageModel<CityStateCountryModel> findAll(@RequestParam(value = "stateId", required = false) String externalStateId, // TODO: Criar validação para ids UUID
                                                    @RequestParam(value = "name", required = false) String name, // TODO: Criar validação para ids UUID
                                                    Pageable pageable) {
        return cityStateCountryAssembler.toPageModel(cityRegisterService.findAll(externalStateId, name, pageable));
    }

    @Override
    @DeleteMapping("/{id}")
    @CheckSecurity.City.CanDelete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeByExternalId(@PathVariable(name = "id") String externalId) {
        cityRegisterService.removeByExternalId(externalId);
    }
}
