package br.com.bdk.eventsmanager.core.validator;

import br.com.bdk.eventsmanager.core.util.PasswordUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.PasswordData;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        var passwordValidator = new org.passay.PasswordValidator(PasswordUtil.getDefaultRules());
        var result = passwordValidator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }

        //Sending one message each time failed validation.
        context.buildConstraintViolationWithTemplate(passwordValidator.getMessages(result).stream().findFirst().orElse("Invalid password"))
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;

    }

}
