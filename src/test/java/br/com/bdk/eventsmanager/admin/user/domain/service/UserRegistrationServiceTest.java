package br.com.bdk.eventsmanager.admin.user.domain.service;

import br.com.bdk.eventsmanager.admin.company.domain.service.CompanyRegistrationService;
import br.com.bdk.eventsmanager.admin.user.domain.model.User;
import br.com.bdk.eventsmanager.admin.user.domain.model.exception.UserNotFoundException;
import br.com.bdk.eventsmanager.admin.user.domain.repository.UserRepository;
import br.com.bdk.eventsmanager.admin.user.domain.validator.UserRegistrationValidator;
import br.com.bdk.eventsmanager.common.EnvironmentMock;
import br.com.bdk.eventsmanager.core.email.EmailSendingFactory;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ValidationAutoConfiguration.class)
@SpringBootTest(classes = UserRegistrationService.class)
class UserRegistrationServiceTest {

    @Autowired
    private UserRegistrationService userRegisterService;

    @MockitoBean
    private CompanyRegistrationService companyRegistrationService;

    @MockitoBean
    private UserRegistrationValidator userRegisterValidator;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private EmailSendingFactory emailSendingFactory;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Nested
    class WhenSearchUserById {

        @Test
        void shouldSearchOk() {
            final var userId = 22L;
            final var userExpected = EnvironmentMock.mock(User.class);
            when(userRepository.findById(userId))
                    .thenReturn(Optional.of(userExpected));
            final var userFound = userRegisterService.findByIdOrFail(userId);
            assertThat(userFound)
                    .usingRecursiveComparison()
                    .isEqualTo(userExpected);
        }

        @Test
        void shouldThrowExceptionWhenIdIsNull() {
            assertThatThrownBy(() -> userRegisterService.findByIdOrFail(null))
                    .hasMessage("findByIdOrFail.id: não deve ser nulo")
                    .isInstanceOf(ConstraintViolationException.class);

            verifyNoInteractions(userRepository);
        }

        @Test
        void shouldThrowExceptionWhenUserNotFound() {
            final var userId = 1L;
            when(userRepository.findById(userId)).thenReturn(Optional.empty());
            assertThatThrownBy(() -> userRegisterService.findByIdOrFail(userId))
                    .hasMessage("Não foi encontrado um usuário com código '%s'".formatted(userId))
                    .isInstanceOf(UserNotFoundException.class);
        }

    }
}