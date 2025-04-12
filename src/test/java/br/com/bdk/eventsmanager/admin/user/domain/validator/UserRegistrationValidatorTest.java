package br.com.bdk.eventsmanager.admin.user.domain.validator;

import br.com.bdk.eventsmanager.admin.company.domain.model.Company;
import br.com.bdk.eventsmanager.admin.user.domain.model.User;
import br.com.bdk.eventsmanager.admin.user.domain.model.exception.DuplicateLoginUserException;
import br.com.bdk.eventsmanager.admin.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRegistrationValidatorTest {

    @InjectMocks
    private UserRegistrationValidator userRegisterValidator;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        ReflectionTestUtils.setField(user, "id", 1L);
        user.setEmail("email@email.com");
        user.setActive(Boolean.TRUE);
        user.setLogin("cristian.berti");
        user.setName("Cristian Berti");
        user.setCompany(new Company());
    }

    @Test
    void shouldValidate() {
        when(userRepository.findByLoginIgnoreCase(user.getLogin())).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> userRegisterValidator.validate(user));
    }

    @Test
    void shouldValidateWhenIsSameUser() {
        when(userRepository.findByLoginIgnoreCase(user.getLogin()))
                .thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> userRegisterValidator.validate(user));
    }

    @Test
    void shouldThrowExceptionWhenExistsUserWithSameLogin() {
        var existingUser = new User();
        when(userRepository.findByLoginIgnoreCase(user.getLogin())).thenReturn(Optional.of(existingUser));
        var exception = Assertions.assertThrows(DuplicateLoginUserException.class,
                () -> userRegisterValidator.validate(user));
        assertEquals("Já existe um usuário com login '%s'".formatted(user.getLogin()), exception.getMessage());
    }
}