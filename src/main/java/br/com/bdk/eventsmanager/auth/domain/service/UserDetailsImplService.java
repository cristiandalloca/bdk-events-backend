package br.com.bdk.eventsmanager.auth.domain.service;

import br.com.bdk.eventsmanager.admin.user.domain.model.exception.UserNotFoundException;
import br.com.bdk.eventsmanager.auth.domain.model.UserDetailsDto;
import br.com.bdk.eventsmanager.auth.domain.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserDetailsImplService implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String loginOrEmail) throws UsernameNotFoundException {
        final var user = authRepository.findByLoginOrEmail(loginOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário inexistente ou senha inválida"));
        this.loadPrivileges(user);
        return user;
    }

    private void loadPrivileges(UserDetailsDto user) {
        user.setPrivileges(new HashSet<>(authRepository.findAllPrivilegesById(user.getId())));
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserByExternalId(@NonNull String externalId) {
        final var user = authRepository.findByExternalId(externalId)
                .orElseThrow(() -> new UserNotFoundException(externalId));
        this.loadPrivileges(user);
        return user;
    }
}
