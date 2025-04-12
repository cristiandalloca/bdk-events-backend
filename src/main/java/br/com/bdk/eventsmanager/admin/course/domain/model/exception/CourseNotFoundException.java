package br.com.bdk.eventsmanager.admin.course.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.ResourceNotFoundException;

public class CourseNotFoundException extends ResourceNotFoundException {

    public CourseNotFoundException(String externalId) {
        super("Não foi encontrado um curso com código '%s'".formatted(externalId));
    }
}
