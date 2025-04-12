package br.com.bdk.eventsmanager.admin.course.domain.model.exception;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public class DuplicateNameCourseException extends BusinessException {

    public DuplicateNameCourseException(String name) {
        super("Já existe um curso com nome '%s'".formatted(name));
    }
}
