package com.swansoftwaresolutions.jirareport.web.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * @author Vladimir Martynyuk
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@NotNull
@Constraint(validatedBy = PasswordsEqualConstraintValidator.class)
public @interface PasswordsEqualConstraint {
    String message() default "Wrong password";

    String field();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
