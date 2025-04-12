package br.com.bdk.eventsmanager.admin.course.api.v1.controller.openapi;

import br.com.bdk.eventsmanager.admin.course.api.v1.model.CourseModel;
import br.com.bdk.eventsmanager.admin.course.api.v1.model.input.CourseInput;
import br.com.bdk.eventsmanager.core.api.model.PageModel;
import br.com.bdk.eventsmanager.core.api.openapi.ParameterQueryPageable;
import br.com.bdk.eventsmanager.core.api.openapi.RegisterController;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;

@Tag(name = "Courses")
@SecurityRequirement(name = "bearerAuth")
public interface CourseRegisterControllerOpenApi extends RegisterController<CourseInput, CourseModel> {

    @Override
    @Operation(summary = "Create a course")
    CourseModel create(CourseInput input);

    @Override
    @Operation(summary = "Find a course by id")
    CourseModel findByExternalId(String externalId);

    @Override
    @Operation(summary = "Update a course by id")
    CourseModel updateByExternalId(String externalId, CourseInput input);

    @Override
    @Operation(summary = "Remove a course by id")
    void removeByExternalId(String externalId);

    @Operation(summary = "Search a course")
    @ParameterQueryPageable
    PageModel<CourseModel> findAll(@Parameter(description = Description.COURSE_NAME) String name,
                                   @Parameter(description = Description.COURSE_ACTIVE) Boolean active,
                                   @Parameter(hidden = true) Pageable pageable);
}
