package br.com.bdk.eventsmanager.admin.user.domain.validator;

import br.com.bdk.eventsmanager.admin.user.domain.model.User;
import br.com.bdk.eventsmanager.admin.user.domain.model.exception.DuplicateEmailUserException;
import br.com.bdk.eventsmanager.admin.user.domain.model.exception.DuplicateLoginUserException;
import br.com.bdk.eventsmanager.admin.user.domain.repository.UserRepository;
import br.com.bdk.eventsmanager.core.exception.BusinessException;
import br.com.bdk.eventsmanager.core.validator.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserRegistrationValidator implements RegistrationValidator<User> {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public void validate(@NonNull User user) throws BusinessException {
        var existingUserByLogin = userRepository.findByLoginIgnoreCase(user.getLogin());
        if (existingUserByLogin.isPresent() && this.isNotSame(user, existingUserByLogin.get())) {
            throw new DuplicateLoginUserException(user.getLogin());
        }

        var existingUserByEmail = userRepository.findByEmailIgnoreCase(user.getEmail());
        if (existingUserByEmail.isPresent() && this.isNotSame(user, existingUserByEmail.get())) {
            throw new DuplicateEmailUserException(user.getEmail());
        }
    }

}
