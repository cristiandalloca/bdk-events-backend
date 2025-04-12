package br.com.bdk.eventsmanager.auth.domain.service;

import br.com.bdk.eventsmanager.auth.domain.model.UserDetailsDto;
import br.com.bdk.eventsmanager.auth.domain.model.exception.LoggedUserNotFoundException;
import br.com.bdk.eventsmanager.common.EnvironmentMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class LoggedUserServiceTest {

    @InjectMocks
    private LoggedUserService loggedUserService;

    @BeforeEach
    void setUp() {
        final var principal = EnvironmentMock.mock(UserDetailsDto.class);
        final var authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Nested
    class WhenGetLoggedUser {

        @Test
        void shouldReturnLoggedUserId() {
            final var loggedUserDetails = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            final var loggedUserId = loggedUserService.getUserId();
            assertThat(loggedUserId).isEqualTo(loggedUserDetails.getId());
        }

        @Test
        void shouldThrowExceptionWhenAuthenticationNotFound() {
            SecurityContextHolder.getContext().setAuthentication(null);
            assertThatThrownBy(() -> loggedUserService.getUserId())
                    .isInstanceOf(LoggedUserNotFoundException.class)
                    .hasMessage("Não foi possível encontrar um usuário logado");
        }

        @Test
        void shouldThrowExceptionWhenUserDetailsOfLoggedUserNotFound() {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(null, null));
            assertThatThrownBy(() -> loggedUserService.getUserId())
                    .isInstanceOf(LoggedUserNotFoundException.class)
                    .hasMessage("Não foi possível encontrar um usuário logado");
        }

        @Test
        void shouldThrowExceptionWhenPrincipalIsNotInstanceOfUserDetailsDto() {
            SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthenticationToken("1L", "anonymousUser", List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))));
            assertThatThrownBy(() -> loggedUserService.getUserId())
                    .isInstanceOf(LoggedUserNotFoundException.class)
                    .hasMessage("Não foi possível encontrar um usuário logado");
        }
    }
}