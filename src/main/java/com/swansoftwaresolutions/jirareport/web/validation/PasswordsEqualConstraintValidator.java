package com.swansoftwaresolutions.jirareport.web.validation;

import com.swansoftwaresolutions.jirareport.core.dto.user.PasswordDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Vladimir Martynyuk
 */
public class PasswordsEqualConstraintValidator implements ConstraintValidator<PasswordsEqualConstraint, Object> {

    private String errorMessage;

    @Override
    public void initialize(PasswordsEqualConstraint constraintAnnotation) {
        errorMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object candidate, ConstraintValidatorContext context) {
        PasswordDto passwordDto = (PasswordDto) candidate;
        if (!passwordDto.getNewPassword().equals(passwordDto.getNewPasswordAgain())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorMessage).addPropertyNode("newPasswordAgain").addConstraintViolation();
            return false;
        }
        return true;
    }
}
