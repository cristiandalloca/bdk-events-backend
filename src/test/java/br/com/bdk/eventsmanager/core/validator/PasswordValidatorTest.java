package br.com.bdk.eventsmanager.core.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PasswordValidatorTest {

    private ConstraintValidatorContext context;
    private ConstraintValidatorContext.ConstraintViolationBuilder contextBuilder;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        context = Mockito.mock(ConstraintValidatorContext.class);
        contextBuilder = Mockito.mock(ConstraintValidatorContext.ConstraintViolationBuilder.class);
    }

    private void mockContextCall() {
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(contextBuilder);
        when(contextBuilder.addConstraintViolation()).thenReturn(context);
    }

    @Test
    void shouldReturnValidPassword() {
        var result = new PasswordValidator().isValid("Cristi@n06021997", context);
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnInvalidPasswordMinLength() {
        mockContextCall();
        var result = new PasswordValidator().isValid(" ", context);
        assertThat(result).isFalse();
        verify(context).buildConstraintViolationWithTemplate(stringArgumentCaptor.capture());
        String messagePasswordValidation = stringArgumentCaptor.getValue();
        assertThat(messagePasswordValidation)
                .isEqualTo("Password must be 8 or more characters in length.");
    }

    @Test
    void shouldReturnInvalidPasswordMaxLength() {
        mockContextCall();
        var result = new PasswordValidator().isValid(RandomStringUtils.secure().nextAlphanumeric(21), context);
        assertThat(result).isFalse();
        verify(context).buildConstraintViolationWithTemplate(stringArgumentCaptor.capture());
        String messagePasswordValidation = stringArgumentCaptor.getValue();
        assertThat(messagePasswordValidation)
                .isEqualTo("Password must be no more than 20 characters in length.");
    }

    @Test
    void shouldReturnInvalidPasswordMinUpperCase() {
        mockContextCall();
        var result = new PasswordValidator().isValid(RandomStringUtils.secure().nextAlphanumeric(20).toLowerCase(), context);
        assertThat(result).isFalse();
        verify(context).buildConstraintViolationWithTemplate(stringArgumentCaptor.capture());
        String messagePasswordValidation = stringArgumentCaptor.getValue();
        assertThat(messagePasswordValidation)
                .isEqualTo("Password must contain 1 or more uppercase characters.");
    }

    @Test
    void shouldReturnInvalidPasswordMinLowerCase() {
        mockContextCall();
        var result = new PasswordValidator().isValid(RandomStringUtils.secure().nextAlphanumeric(20).toUpperCase(), context);
        assertThat(result).isFalse();
        verify(context).buildConstraintViolationWithTemplate(stringArgumentCaptor.capture());
        String messagePasswordValidation = stringArgumentCaptor.getValue();
        assertThat(messagePasswordValidation)
                .isEqualTo("Password must contain 1 or more lowercase characters.");
    }

    @Test
    void shouldReturnInvalidPasswordMinDigitCharacter() {
        mockContextCall();
        var result = new PasswordValidator().isValid(RandomStringUtils.secure().nextAlphabetic(20), context);
        assertThat(result).isFalse();
        verify(context).buildConstraintViolationWithTemplate(stringArgumentCaptor.capture());
        String messagePasswordValidation = stringArgumentCaptor.getValue();
        assertThat(messagePasswordValidation)
                .isEqualTo("Password must contain 1 or more digit characters.");
    }

    @Test
    void shouldReturnInvalidPasswordMinSpecialCharacter() {
        mockContextCall();
        var result = new PasswordValidator().isValid("Cristian06021997", context);
        assertThat(result).isFalse();
        verify(context).buildConstraintViolationWithTemplate(stringArgumentCaptor.capture());
        String messagePasswordValidation = stringArgumentCaptor.getValue();
        assertThat(messagePasswordValidation)
                .isEqualTo("Password must contain 1 or more special characters.");
    }

}