package br.com.bdk.eventsmanager.admin.invitationtype.domain.validator;

import br.com.bdk.eventsmanager.admin.invitationtype.domain.model.InvitationType;
import br.com.bdk.eventsmanager.admin.invitationtype.domain.model.exception.DuplicateInvitationTypeException;
import br.com.bdk.eventsmanager.admin.invitationtype.domain.repository.InvitationTypeRepository;
import br.com.bdk.eventsmanager.core.exception.BusinessException;
import br.com.bdk.eventsmanager.core.validator.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvitationTypeRegistrationValidator implements RegistrationValidator<InvitationType> {

    private final InvitationTypeRepository invitationTypeRepository;

    @Override
    public void validate(@NonNull InvitationType invitationType) throws BusinessException {
        var existingCompanyByName = invitationTypeRepository.findByNameIgnoreCase(invitationType.getName());
        if (existingCompanyByName.isPresent() && this.isNotSame(invitationType, existingCompanyByName.get())) {
            throw new DuplicateInvitationTypeException(invitationType.getName());
        }
    }
}
