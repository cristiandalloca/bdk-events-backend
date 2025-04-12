package br.com.bdk.eventsmanager.admin.user.domain.service;

import br.com.bdk.eventsmanager.admin.company.domain.service.CompanyRegistrationService;
import br.com.bdk.eventsmanager.admin.user.domain.model.User;
import br.com.bdk.eventsmanager.admin.user.domain.model.exception.UserNotFoundException;
import br.com.bdk.eventsmanager.admin.user.domain.model.exception.UserPasswordDoesNotMatchException;
import br.com.bdk.eventsmanager.admin.user.domain.repository.UserRepository;
import br.com.bdk.eventsmanager.admin.user.domain.validator.UserRegistrationValidator;
import br.com.bdk.eventsmanager.core.email.EmailSendingFactory;
import br.com.bdk.eventsmanager.core.email.EmailSendingService;
import br.com.bdk.eventsmanager.core.exception.ResourceInUseException;
import br.com.bdk.eventsmanager.core.service.RegistrationService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;


@Service
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserRegistrationService implements RegistrationService<User> {

    private final CompanyRegistrationService companyRegistrationService;
    private final UserRegistrationValidator userRegisterValidator;
    private final UserRepository userRepository;
    private final EmailSendingFactory emailSendingFactory;
    private final PasswordEncoder passwordEncoder;
    private static final String EXCEPTION_MESSAGE_USER_IN_USE
            = "Usuário de código '%s' não pode ser removido, pois está em uso";


    @Override
    @NonNull
    @Transactional
    public User validateAndSave(@NonNull User user) {
        user.setCompany(companyRegistrationService.findByExternalId(user.getCompany().getExternalId()));
        userRegisterValidator.validate(user);
        if (user.isNew()) {
            var message = EmailSendingService.Message.builder()
                    .body("Olá %s. Sua senha é: %s".formatted(user.getName(), user.createNewPassword(passwordEncoder)))
                    .recipient(user.getEmail())
                    .subject("Nova senha")
                    .build();
            emailSendingFactory.getService().send(message);
        }
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findByIdOrFail(@NotNull Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public User findByExternalId(@NonNull String externalId) {
        return findByExternalIdOrFail(externalId);
    }

    private User findByExternalIdOrFail(String externalId) {
        return userRepository.findByExternalId(externalId)
                .orElseThrow(() -> new UserNotFoundException(externalId));
    }

    @NonNull
    @Transactional(readOnly = true)
    public User findByExternalIdWithProfiles(@NonNull String externalId) {
        var user = this.findByExternalIdOrFail(externalId);
        Hibernate.initialize(user.getProfiles());
        return user;
    }

    @NonNull
    @Transactional(readOnly = true)
    public User findByExternalIdWithPrivileges(@NonNull String externalId) {
        return userRepository.findByExternalIdWithPrivileges(externalId)
                .orElseThrow(() -> new UserNotFoundException(externalId));
    }

    @Override
    @Transactional
    public void removeByExternalId(@NonNull String externalId) {
        try {
            userRepository.delete(this.findByExternalIdOrFail(externalId));
            userRepository.flush();
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage(), e);
            throw new ResourceInUseException(EXCEPTION_MESSAGE_USER_IN_USE.formatted(externalId));
        }

    }

    @NonNull
    @Transactional(readOnly = true)
    public Page<User> findAll(@Nullable String name, @Nullable String login, @NonNull Pageable pageable) {
        return userRepository.findAllByNameAndLogin(StringUtils.trimToNull(name), StringUtils.trimToNull(login), pageable);
    }

    @Transactional
    public void changePasswordByExternalId(String externalId, String actualPassword, String newPassword) {
        var user = this.findByExternalIdOrFail(externalId);
        if (!user.getPassword().equals(actualPassword)) {
            throw new UserPasswordDoesNotMatchException();
        }
        user.changePassword(newPassword);
        userRepository.save(user);
    }
}
