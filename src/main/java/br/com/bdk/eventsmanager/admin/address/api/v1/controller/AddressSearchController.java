package br.com.bdk.eventsmanager.admin.address.api.v1.controller;

import br.com.bdk.eventsmanager.admin.address.api.v1.AddressModelAssembler;
import br.com.bdk.eventsmanager.admin.address.api.v1.controller.openapi.AddressSearchControllerOpenApi;
import br.com.bdk.eventsmanager.admin.address.api.v1.model.AddressModel;
import br.com.bdk.eventsmanager.admin.address.domain.service.AddressSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/addresses")
@RequiredArgsConstructor
public class AddressSearchController implements AddressSearchControllerOpenApi {

    private final AddressModelAssembler addressModelAssembler;
    private final AddressSearchService addressSearchService;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public AddressModel findByPostalCode(@RequestParam("postalCode") String postalCode) {
        return addressModelAssembler.toModel(addressSearchService.findByPostalCode(postalCode));
    }

}
