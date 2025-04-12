package br.com.bdk.eventsmanager.core.validator;

import br.com.bdk.eventsmanager.core.exception.BusinessException;

public interface RegistrationValidator<T> {

    void validate(T entity) throws BusinessException;

    default boolean isNotSame(T entity1, T entity2) {
        return !entity1.equals(entity2);
    }

}
