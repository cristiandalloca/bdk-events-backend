package br.com.bdk.eventsmanager.admin.privilege.domain.validator;

import br.com.bdk.eventsmanager.admin.privilege.domain.model.Privilege;
import br.com.bdk.eventsmanager.admin.privilege.domain.model.exception.DuplicateNamePrivilegeException;
import br.com.bdk.eventsmanager.admin.privilege.domain.repository.PrivilegeRepository;
import br.com.bdk.eventsmanager.core.exception.BusinessException;
import br.com.bdk.eventsmanager.core.validator.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrivilegeRegistrationValidator implements RegistrationValidator<Privilege> {

    private final PrivilegeRepository privilegeRepository;

    @Override
    public void validate(@NonNull Privilege privilege) throws BusinessException {
        var existingPrivilegeByName = privilegeRepository.findByNameIgnoreCase(privilege.getName());
        if (existingPrivilegeByName.isPresent() && this.isNotSame(privilege, existingPrivilegeByName.get())) {
            throw new DuplicateNamePrivilegeException(privilege.getName());
        }

    }

}
