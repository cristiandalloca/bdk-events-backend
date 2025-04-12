package br.com.bdk.eventsmanager.admin.privilege.domain.service;

import br.com.bdk.eventsmanager.admin.privilege.domain.model.Privilege;
import br.com.bdk.eventsmanager.admin.privilege.domain.model.exception.PrivilegeNotFoundException;
import br.com.bdk.eventsmanager.admin.privilege.domain.repository.PrivilegeRepository;
import br.com.bdk.eventsmanager.admin.privilege.domain.validator.PrivilegeRegistrationValidator;
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
public class PrivilegeRegistrationService implements RegistrationService<Privilege> {

    private final PrivilegeRepository privilegeRepository;
    private final PrivilegeRegistrationValidator privilegeRegisterValidator;
    private static final String EXCEPTION_MESSAGE_PRIVILEGE_IN_USE
            = "Privilégio de código '%s' não pode ser removido, pois está em uso";

    @NonNull
    @Override
    @Transactional
    public Privilege validateAndSave(@NonNull Privilege privilege) {
        privilegeRegisterValidator.validate(privilege);
        return privilegeRepository.save(privilege);
    }

    @NonNull
    @Override
    @Transactional(readOnly = true)
    public Privilege findByExternalId(@NonNull String externalId) {
        return findByExternalIdOrFail(externalId);
    }

    private Privilege findByExternalIdOrFail(String externalId) {
        return privilegeRepository.findByExternalId(externalId)
                .orElseThrow(() -> new PrivilegeNotFoundException(externalId));
    }

    @Override
    @Transactional
    public void removeByExternalId(@NonNull String externalId) {
        try {
            privilegeRepository.delete(this.findByExternalIdOrFail(externalId));
            privilegeRepository.flush();
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            throw new ResourceInUseException(EXCEPTION_MESSAGE_PRIVILEGE_IN_USE.formatted(externalId));
        }
    }

    @NonNull
    @Transactional(readOnly = true)
    public List<Privilege> findAll(@Nullable String description) {
        return privilegeRepository.findAllByDescription(StringUtils.trimToNull(description));
    }
}
