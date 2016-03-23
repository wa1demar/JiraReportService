package com.swansoftwaresolutions.jirareport.web.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Vladimir Martynyuk
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueConstraintValidator.class)
@Documented
public @interface Unique {
    String message() default "{Not Unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String property() default "id";

    Class<?> value();
}
