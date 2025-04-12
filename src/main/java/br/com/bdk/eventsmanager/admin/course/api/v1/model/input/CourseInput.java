package br.com.bdk.eventsmanager.admin.course.api.v1.model.input;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Example;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CourseInput {

    @NotBlank
    @Size(max = 255)
    @Schema(example = Example.COURSE_NAME, description = Description.COURSE_NAME)
    private String name;

    @NotNull
    @Schema(example = Example.BOOLEAN, description = Description.COURSE_ACTIVE)
    private Boolean active;

}
