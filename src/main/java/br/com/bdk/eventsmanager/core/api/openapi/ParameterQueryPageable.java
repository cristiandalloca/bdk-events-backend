package br.com.bdk.eventsmanager.core.api.openapi;

import br.com.bdk.eventsmanager.core.springdoc.SpringDocConstantsUtil.Description;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameter(in = ParameterIn.QUERY,
        description = Description.NUMBER_PAGE,
        name = "page",
        schema = @Schema(type = "integer", defaultValue = "0"))
@Parameter(in = ParameterIn.QUERY,
        description = Description.PAGE_SIZE,
        name = "size",
        schema = @Schema(type = "integer", defaultValue = "25"))
public @interface ParameterQueryPageable {
}
