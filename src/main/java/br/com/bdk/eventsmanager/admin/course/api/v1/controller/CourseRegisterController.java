package br.com.bdk.eventsmanager.admin.course.api.v1.controller;

import br.com.bdk.eventsmanager.admin.course.api.v1.assembler.CourseModelAssembler;
import br.com.bdk.eventsmanager.admin.course.api.v1.controller.openapi.CourseRegisterControllerOpenApi;
import br.com.bdk.eventsmanager.admin.course.api.v1.disassembler.CourseInputDisassembler;
import br.com.bdk.eventsmanager.admin.course.api.v1.model.CourseModel;
import br.com.bdk.eventsmanager.admin.course.api.v1.model.input.CourseInput;
import br.com.bdk.eventsmanager.admin.course.domain.service.CourseRegistrationService;
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
@RequestMapping(path = "/v1/courses")
public class CourseRegisterController implements CourseRegisterControllerOpenApi {

    private final CourseInputDisassembler disassembler;
    private final CourseModelAssembler assembler;
    private final CourseRegistrationService service;

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Course.CanCreate
    @ResponseStatus(HttpStatus.CREATED)
    public CourseModel create(@RequestBody @Valid CourseInput input) {
        return assembler.toModel(service.validateAndSave(disassembler.toEntity(input)));
    }

    @Override
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Course.CanRead
    @ResponseStatus(HttpStatus.OK)
    public CourseModel findByExternalId(@PathVariable("id") String externalId) {
        return assembler.toModel(service.findByExternalId(externalId));
    }

    @Override
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Course.CanUpdate
    @ResponseStatus(HttpStatus.OK)
    public CourseModel updateByExternalId(@PathVariable("id") String externalId, @RequestBody @Valid CourseInput input) {
        var courseToUpdate = service.findByExternalId(externalId);
        disassembler.copyToEntity(input, courseToUpdate);
        return assembler.toModel(service.validateAndSave(courseToUpdate));
    }

    @Override
    @DeleteMapping("/{id}")
    @CheckSecurity.Course.CanDelete
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeByExternalId(@PathVariable("id") String externalId) {
        service.removeByExternalId(externalId);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @CheckSecurity.Course.CanRead
    @ResponseStatus(HttpStatus.OK)
    public PageModel<CourseModel> findAll(@RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "active", required = false) Boolean active,
                                          Pageable pageable) {
        return assembler.toPageModel(service.findAll(name, active, pageable));
    }
}
