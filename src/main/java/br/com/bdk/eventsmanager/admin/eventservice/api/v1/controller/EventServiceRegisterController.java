package br.com.bdk.eventsmanager.admin.eventservice.api.v1.controller;

import br.com.bdk.eventsmanager.admin.eventservice.api.v1.assembler.EventServiceCompleteModelAssembler;
import br.com.bdk.eventsmanager.admin.eventservice.api.v1.assembler.EventServiceMeasurementTypeModelAssembler;
import br.com.bdk.eventsmanager.admin.eventservice.api.v1.controller.openapi.EventServiceRegisterControllerOpenApi;
import br.com.bdk.eventsmanager.admin.eventservice.api.v1.disassembler.EventServiceCompleteInputDisassembler;
import br.com.bdk.eventsmanager.admin.eventservice.api.v1.model.EventServiceCompleteModel;
import br.com.bdk.eventsmanager.admin.eventservice.api.v1.model.EventServiceMeasurementTypeModel;
import br.com.bdk.eventsmanager.admin.eventservice.api.v1.model.input.EventServiceCompleteInput;
import br.com.bdk.eventsmanager.admin.eventservice.domain.model.EventServiceMeasurementType;
import br.com.bdk.eventsmanager.admin.eventservice.domain.service.EventServiceRegistrationService;
import br.com.bdk.eventsmanager.auth.CheckSecurity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/event-services")
public class EventServiceRegisterController implements EventServiceRegisterControllerOpenApi {

    private final EventServiceRegistrationService eventServiceRegisterService;
    private final EventServiceCompleteInputDisassembler eventServiceCompleteInputDisassembler;
    private final EventServiceCompleteModelAssembler eventServiceCompleteModelAssembler;
    private final EventServiceMeasurementTypeModelAssembler measurementTypeModelAssembler;

    @Override
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Event.CanRead
    @ResponseStatus(HttpStatus.OK)
    public EventServiceCompleteModel findByExternalId(@PathVariable("id") String externalId) {
        return eventServiceCompleteModelAssembler.toModel(eventServiceRegisterService.findByExternalId(externalId));
    }

    @Override
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Event.CanUpdate
    @ResponseStatus(HttpStatus.OK)
    public EventServiceCompleteModel updateByExternalId(@PathVariable("id") String externalId, @RequestBody @Valid EventServiceCompleteInput input) {
        var eventServiceToUpdate = eventServiceRegisterService.findByExternalId(externalId);
        eventServiceCompleteInputDisassembler.copyToEntity(input, eventServiceToUpdate);
        return eventServiceCompleteModelAssembler.toModel(eventServiceRegisterService.validateAndSave(eventServiceToUpdate));
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Event.CanCreate
    @ResponseStatus(HttpStatus.CREATED)
    public EventServiceCompleteModel create(@RequestBody @Valid EventServiceCompleteInput input) {
        var eventService = eventServiceCompleteInputDisassembler.toEntity(input);
        return eventServiceCompleteModelAssembler.toModel(eventServiceRegisterService.validateAndSave(eventService));
    }

    @Override
    @DeleteMapping(path = "/{id}")
    @CheckSecurity.Event.CanDelete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeByExternalId(@PathVariable("id") String eventServiceExternalId) {
        eventServiceRegisterService.removeByExternalId(eventServiceExternalId);
    }

    @Override
    @GetMapping(path = "/measurement-types", produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Event.CanRead
    @ResponseStatus(HttpStatus.OK)
    public Collection<EventServiceMeasurementTypeModel> findAllMeasurementTypes() {
        return measurementTypeModelAssembler.toCollectionModel(List.of(EventServiceMeasurementType.values()));
    }

}
