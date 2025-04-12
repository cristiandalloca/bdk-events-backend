package br.com.bdk.eventsmanager.admin.service.api.v1.controller;

import br.com.bdk.eventsmanager.admin.service.api.v1.assembler.ServicePhotoModelAssembler;
import br.com.bdk.eventsmanager.admin.service.api.v1.controller.openapi.ServicePhotoRegisterControllerOpenApi;
import br.com.bdk.eventsmanager.admin.service.api.v1.disassembler.ServicePhotoInputDisassembler;
import br.com.bdk.eventsmanager.admin.service.api.v1.model.ServicePhotoModel;
import br.com.bdk.eventsmanager.admin.service.api.v1.model.input.ServicePhotoInput;
import br.com.bdk.eventsmanager.admin.service.domain.service.ServicePhotoRegisterService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/services/{id}/photos")
public class ServicePhotoRegisterController implements ServicePhotoRegisterControllerOpenApi {

    private final ServicePhotoRegisterService service;
    private final ServicePhotoInputDisassembler disassembler;
    private final ServicePhotoModelAssembler assembler;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Collection<ServicePhotoModel> findAllPhotosByServiceExternalId(@PathVariable("id") String serviceExternalId) {
        return assembler.toCollectionModel(service.findAllByServiceExternalId(serviceExternalId));
    }

    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ServicePhotoModel insertOrUpdatePhotoByServiceExternalId(@PathVariable("id") String serviceExternalId, @RequestBody @Valid ServicePhotoInput input) {
        input.setServiceExternalId(serviceExternalId);
        var servicePhoto = service.findByServiceExternalIdAndNumber(serviceExternalId, input.getNumber());
        if (servicePhoto != null) {
            disassembler.copyToEntity(input, servicePhoto);
        } else {
            servicePhoto = disassembler.toEntity(input);
        }
        return assembler.toModel(service.saveAndValidate(servicePhoto, input.getPhoto()));
    }

    @Override
    @GetMapping(path = "/{photoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ServicePhotoModel findById(@PathVariable("id") String serviceExternalId, @PathVariable("photoId") String servicePhotoExternalId) {
        return assembler.toModel(service.findByServiceExternalIdAndExternalId(serviceExternalId, servicePhotoExternalId));
    }

    @Override
    @DeleteMapping(path = "/{photoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePhotoByServiceExternalId(@PathVariable("id") String serviceExternalId, @PathVariable("photoId") String servicePhotoExternalId) {
        service.removeByServiceExternalIdAndExternalId(serviceExternalId, servicePhotoExternalId);
    }
}
