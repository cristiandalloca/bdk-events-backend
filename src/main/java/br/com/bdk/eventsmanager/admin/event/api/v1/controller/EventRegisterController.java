package br.com.bdk.eventsmanager.admin.event.api.v1.controller;

import br.com.bdk.eventsmanager.admin.company.domain.validator.CompanyAccessService;
import br.com.bdk.eventsmanager.admin.event.api.v1.assembler.EventModelAssembler;
import br.com.bdk.eventsmanager.admin.event.api.v1.assembler.EventTypeModelAssembler;
import br.com.bdk.eventsmanager.admin.event.api.v1.controller.openapi.EventRegisterControllerOpenApi;
import br.com.bdk.eventsmanager.admin.event.api.v1.disassembler.EventInputDisassembler;
import br.com.bdk.eventsmanager.admin.event.api.v1.model.EventModel;
import br.com.bdk.eventsmanager.admin.event.api.v1.model.EventTypeModel;
import br.com.bdk.eventsmanager.admin.event.api.v1.model.input.EventInput;
import br.com.bdk.eventsmanager.admin.event.api.v1.model.input.EventServiceInput;
import br.com.bdk.eventsmanager.admin.event.domain.model.EventType;
import br.com.bdk.eventsmanager.admin.event.domain.service.EventRegistrationService;
import br.com.bdk.eventsmanager.admin.eventservice.api.v1.assembler.EventServiceModelAssembler;
import br.com.bdk.eventsmanager.admin.eventservice.api.v1.model.EventServiceModel;
import br.com.bdk.eventsmanager.admin.eventservice.domain.service.EventServiceRegistrationService;
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
@RequestMapping(path = "/v1/events")
public class EventRegisterController implements EventRegisterControllerOpenApi {

    private final EventRegistrationService eventRegisterService;
    private final EventInputDisassembler eventInputDisassembler;
    private final EventModelAssembler eventModelAssembler;
    private final EventTypeModelAssembler eventTypeModelAssembler;
    private final EventServiceRegistrationService eventServiceRegisterService;
    private final EventServiceModelAssembler eventServiceModelAssembler;
    private final CompanyAccessService companyAccessService;

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Event.CanCreate
    @ResponseStatus(HttpStatus.CREATED)
    public EventModel create(@RequestBody @Valid EventInput input) {
        return eventModelAssembler.toModel(eventRegisterService.validateAndSave(eventInputDisassembler.toEntity(input)));
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Event.CanRead
    @ResponseStatus(HttpStatus.OK)
    public EventModel findByExternalId(@PathVariable(name = "id") String externalId) {
        return eventModelAssembler.toModel(eventRegisterService.findByExternalId(externalId));
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Event.CanUpdate
    @ResponseStatus(HttpStatus.OK)
    public EventModel updateByExternalId(@PathVariable(name = "id") String externalId, @RequestBody @Valid EventInput input) {
        var eventToUpdate = eventRegisterService.findByExternalId(externalId);
        eventInputDisassembler.copyToEntity(input, eventToUpdate);
        return eventModelAssembler.toModel(eventRegisterService.validateAndSave(eventToUpdate));
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @CheckSecurity.Event.CanDelete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeByExternalId(@PathVariable(name = "id") String externalId) {
        eventRegisterService.removeByExternalId(externalId);
    }

    @Override
    @GetMapping(value = "/types", produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Event.CanRead
    @ResponseStatus(HttpStatus.OK)
    public Collection<EventTypeModel> findTypes() {
        return eventTypeModelAssembler.toCollectionModel(List.of(EventType.values()));
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Event.CanRead
    @ResponseStatus(HttpStatus.OK)
    public PageModel<EventModel> findAll(@RequestParam(value = "name", required = false) String name,
                                         @RequestParam(value = "companyId", required = false) String companyExternalId,
                                         @RequestParam(value = "type", required = false) EventType type,
                                         @RequestParam(value = "active", required = false) Boolean active, Pageable pageable) {
        companyAccessService.validate(companyExternalId);
        return eventModelAssembler.toPageModel(eventRegisterService.findAll(name, companyExternalId, type, active, pageable));
    }

    @Override
    @GetMapping(path = "/{id}/services", produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Event.CanRead
    @ResponseStatus(HttpStatus.OK)
    public PageModel<EventServiceModel> findServicesByEventExternalId(@PathVariable("id") String eventExternalId,
                                                                      @RequestParam(value = "serviceName", required = false) String serviceName, Pageable pageable) {
        return eventServiceModelAssembler.toPageModel(eventServiceRegisterService.findAllByEventExternalId(eventExternalId, serviceName, pageable));
    }

    @Override
    @PostMapping(path = "/{id}/services")
    @CheckSecurity.Event.CanUpdate
    @ResponseStatus(HttpStatus.CREATED)
    public void addServicesToEvent(@PathVariable("id") String eventExternalId, @RequestBody @Valid EventServiceInput input) {
        eventServiceRegisterService.addServicesToEvent(eventExternalId, input.getServicesIds());
    }

}
