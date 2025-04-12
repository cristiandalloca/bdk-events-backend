package br.com.bdk.eventsmanager.admin.invitationtype.domain.service;

import br.com.bdk.eventsmanager.admin.invitationtype.domain.model.InvitationType;
import br.com.bdk.eventsmanager.admin.invitationtype.domain.model.exception.InvitationTypeNotFoundException;
import br.com.bdk.eventsmanager.admin.invitationtype.domain.repository.InvitationTypeRepository;
import br.com.bdk.eventsmanager.admin.invitationtype.domain.validator.InvitationTypeRegistrationValidator;
import br.com.bdk.eventsmanager.core.exception.ResourceInUseException;
import br.com.bdk.eventsmanager.core.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvitationTypeRegistrationService implements RegistrationService<InvitationType> {

    private static final String EXCEPTION_MESSAGE_INVITATION_TYPE_IN_USE =
            "Tipo de convite de código '%s' não pode ser removido, pois está em uso";

    private final InvitationTypeRepository invitationTypeRepository;
    private final InvitationTypeRegistrationValidator validator;

    @Override
    @NonNull
    @Transactional
    public InvitationType validateAndSave(@NonNull InvitationType invitationType) {
        validator.validate(invitationType);
        return invitationTypeRepository.save(invitationType);
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public InvitationType findByExternalId(@NonNull String externalId) {
        return this.findByExternalIdOrFail(externalId);
    }

    private InvitationType findByExternalIdOrFail(String externalId) {
        return invitationTypeRepository.findByExternalId(externalId)
                .orElseThrow(() -> new InvitationTypeNotFoundException(externalId));
    }

    @Override
    @Transactional
    public void removeByExternalId(@NonNull String externalId) {
        try {
            invitationTypeRepository.delete(this.findByExternalIdOrFail(externalId));
            invitationTypeRepository.flush();
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            throw new ResourceInUseException(EXCEPTION_MESSAGE_INVITATION_TYPE_IN_USE.formatted(externalId));
        }
    }

    @Transactional(readOnly = true)
    public List<InvitationType> findAllByName(@Nullable String name) {
        return invitationTypeRepository.findAllByNameContainingIgnoreCase(StringUtils.trimToNull(name));
    }

}
